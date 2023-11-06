package com.example.rezerwowy.integration;

import com.example.rezerwowy.dtos.PaymentDto;
import com.example.rezerwowy.exceptions.PaymentNotFoundException;
import com.example.rezerwowy.factories.PaymentFactory;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.repositories.PaymentRepository;
import com.example.rezerwowy.services.PaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class PaymentIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;
    @Test
    @DirtiesContext
    void should_createPayment_when_paymentDoesntExist() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase1();
        payment.setId(null);

        // when
        ResponseEntity<PaymentDto> createResponse = restTemplate
                .postForEntity("/payments", payment, PaymentDto.class);

        // then
        Assertions.assertAll(
                () -> assertThat(createResponse.getStatusCode())
                        .isEqualTo(HttpStatus.CREATED),
                () -> assertThat(paymentRepository.existsById(createResponse.getBody().id()))
                        .isTrue()
        );
    }

    @Test
    @DirtiesContext
    void should_giveResponseWithTheSameData_when_createPayment() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase2();
        payment.setId(null);

        // when
        ResponseEntity<PaymentDto> createResponse = restTemplate
                .postForEntity("/payments", payment, PaymentDto.class);

        // then
        Assertions.assertAll(
                () -> assertThat(createResponse.getStatusCode())
                        .isEqualTo(HttpStatus.CREATED),
                () -> assertThat(createResponse.getBody().id())
                        .isNotNull(),
                () -> assertThat(createResponse.getBody().buyer())
                        .isEqualTo(payment.getBuyer()),
                () -> assertThat(createResponse.getBody().date())
                        .isEqualTo(payment.getDate())
        );
    }

    @Test
    @DirtiesContext
    void should_returnBadRequest_when_createPaymentWithTheSameIdAlreadyExists() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase3();
        payment.setId(null);
        paymentRepository.save(payment);

        // when
        ResponseEntity<PaymentDto> createResponse = restTemplate
                .postForEntity("/payments", payment, PaymentDto.class);

        // then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext
    void should_returnBadRequest_when_createPaymentWhereDateIsNull() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase3();
        payment.setId(null);
        payment.setDate(null);

        // when
        ResponseEntity<PaymentDto> createResponse = restTemplate
                .postForEntity("/payments", payment, PaymentDto.class);

        //then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext
    void should_returnBadRequest_when_createPaymentWhereNameIsEmpty() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase3();
        payment.setId(null);
        payment.getBuyer().setName("");

        // when
        ResponseEntity<PaymentDto> createResponse = restTemplate
                .postForEntity("/payments", payment, PaymentDto.class);

        //then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext
    void should_returnCorrectData_when_getExistingPayment() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase1();
        payment.setId(null);
        Payment savedPayment = paymentRepository.save(payment);

        // when
        ResponseEntity<PaymentDto> getResponse = restTemplate
                .getForEntity("/payments/" + payment.getId(), PaymentDto.class);

        // then
        Assertions.assertAll(
                () -> assertThat(getResponse.getStatusCode())
                        .isEqualTo(HttpStatus.OK),
                () -> assertThat(getResponse.getBody().id())
                        .isNotNull(),
                () -> assertThat(getResponse.getBody().buyer())
                        .isEqualTo(savedPayment.getBuyer()),
                () -> assertThat(getResponse.getBody().date())
                        .isEqualTo(savedPayment.getDate())
        );
    }

    @Test
    @DirtiesContext
    void should_returnNotFound_when_getPaymentWithIdThatIsNotPresentInDatabase() {
        // given
        Long paymentId = Long.valueOf(347);

        // when
        ResponseEntity<PaymentDto> getResponse = restTemplate
                .getForEntity("/payments/" + paymentId, PaymentDto.class);

        // then
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void should_deleteInRepository_when_deleteExistingPayment() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase3();
        payment.setId(null);
        Payment savedPayment = paymentRepository.save(payment);

        // when
        restTemplate.delete("/payments/" + savedPayment.getId());

        //then
        assertThat(paymentRepository.existsById(payment.getId())).isFalse();
    }

    @Test
    @DirtiesContext
    void should_throwException_when_deleteNonExistingPayment() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase3();
        paymentRepository.deleteById(payment.getId());

        // when then
        assertThatThrownBy(() -> paymentService.deletePaymentById(payment.getId()))
                .isInstanceOf(PaymentNotFoundException.class);
    }

    @Test
    @DirtiesContext
    void should_returnNotFoundCode_when_deleteNonExistingPayment() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase3();
        paymentRepository.deleteById(payment.getId());

        // when
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/payments/" + payment.getId(), HttpMethod.DELETE, null, Void.class);

        // when
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
