package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.PaymentAlreadyExistsException;
import com.example.rezerwowy.exceptions.PaymentNotFoundException;
import com.example.rezerwowy.factories.PaymentFactory;
import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.models.Seat;
import com.example.rezerwowy.repositories.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(paymentRepository);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_createPayment() {
        //given
        Payment payment = PaymentFactory.createProperPaymentCase1();
        Mockito.when(paymentRepository.save(payment)).thenReturn(payment);

        // when
        paymentService.createPayment(payment);

        // then
        Mockito.verify(paymentRepository).save(payment);
    }

    @Test
    void should_returnObjectWithTheSameDate_when_createPayment() {
        //given
        Payment payment = PaymentFactory.createProperPaymentCase1();
        Mockito.when(paymentRepository.save(payment)).thenReturn(payment);

        // when
        Payment savedPayment = paymentService.createPayment(payment);

        // then
        Assertions.assertAll(
                () -> assertThat(savedPayment.getDate()).isEqualTo(payment.getDate()),
                () -> assertThat(savedPayment.getBuyer()).isEqualTo(payment.getBuyer()),
                () -> assertThat(savedPayment.getReservation()).isEqualTo(payment.getReservation())
        );
    }

    @Test
    void should_throwException_when_paymentWithTheSameIdAlreadyExists() {
        //given
        Payment payment = PaymentFactory.createProperPaymentCase1();
        Mockito.when(paymentRepository.existsById(payment.getId())).thenReturn(true);

        // when then
        assertThatThrownBy(() -> paymentService.createPayment(payment))
                .isInstanceOf(PaymentAlreadyExistsException.class);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_getPaymentById() {
        //given
        Payment payment = PaymentFactory.createProperPaymentCase2();
        Long id = payment.getId();
        Mockito.when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));

        // when
        paymentService.getPaymentById(id);

        // then
        Mockito.verify(paymentRepository).findById(id);
    }

    @Test
    void should_returnObjectWithCorrectData_when_getPaymentByIdAndItExists() {
        //given
        Payment payment = PaymentFactory.createProperPaymentCase2();
        Long id = payment.getId();
        Mockito.when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));

        // when
        Payment foundPayment = paymentService.getPaymentById(id);

        // then
        Assertions.assertAll(
                () -> assertThat(foundPayment.getDate()).isEqualTo(payment.getDate()),
                () -> assertThat(foundPayment.getBuyer()).isEqualTo(payment.getBuyer()),
                () -> assertThat(foundPayment.getReservation()).isEqualTo(payment.getReservation())
        );
    }

    @Test
    void should_throwException_when_getPaymentByIdButItDoesntExist() {
        //given
        Payment payment = PaymentFactory.createProperPaymentCase2();
        Long id = payment.getId();
        Mockito.when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> paymentService.getPaymentById(id))
                .isInstanceOf(PaymentNotFoundException.class);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_deletePaymentById() {
        //given
        Payment payment = PaymentFactory.createProperPaymentCase1();
        Long id = payment.getId();
        Mockito.when(paymentRepository.existsById(id)).thenReturn(true);

        // when
        paymentService.deletePaymentById(id);

        // then
        Mockito.verify(paymentRepository).deleteById(id);
    }

    @Test
    void should_containCorrectSum_when_generatePaymentReceipt() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase1();
        int seatsCount = 1;
        BigDecimal pricePerSeat = BigDecimal.valueOf(10.23);
        BigDecimal totalPrice = pricePerSeat.multiply(BigDecimal.valueOf(seatsCount));
        mockDataForReceipt(payment, seatsCount, pricePerSeat);

        // when
        String receipt = paymentService.generatePaymentReceipt(payment.getId());

        // then
        assertThat(receipt).contains(totalPrice.toString());
    }

    @Test
    void should_containPricePerSeatAsManyTimesAsSeats_when_generatePaymentReceiptForReservationFor3Seats() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase1();
        int seatsCount = 3;
        BigDecimal pricePerSeat = BigDecimal.valueOf(10.23);
        int expectedPriceOccurrences = 3;
        mockDataForReceipt(payment, seatsCount, pricePerSeat);

        // when
        String receipt = paymentService.generatePaymentReceipt(payment.getId());
        int actualPriceOccurrences = countSubstringOccurrences(receipt, pricePerSeat.toString());

        // then
        assertThat(actualPriceOccurrences).isGreaterThanOrEqualTo(expectedPriceOccurrences);
    }

    @Test
    void should_containCorrectTaxAmount_when_generatePaymentReceipt() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase1();
        int seatsCount = 2;
        BigDecimal pricePerSeat = BigDecimal.valueOf(11.99);
        double taxRateInPercent = PaymentService.TAX_IN_PERCENT;
        BigDecimal expectedTaxAmount = pricePerSeat.multiply(BigDecimal.valueOf(seatsCount))
                .multiply(BigDecimal.valueOf(taxRateInPercent))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.CEILING);
        mockDataForReceipt(payment, seatsCount, pricePerSeat);

        // when
        String receipt = paymentService.generatePaymentReceipt(payment.getId());

        // then
        assertThat(receipt).contains(expectedTaxAmount.toString());
    }

    @Test
    void should_containCorrectDate_when_generatePaymentReceipt() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase1();
        LocalDate date = LocalDate.of(2015, 7, 3);
        payment.setDate(date);
        int seatsCount = 4;
        BigDecimal pricePerSeat = BigDecimal.valueOf(5.35);
        mockDataForReceipt(payment, seatsCount, pricePerSeat);

        // when
        String receipt = paymentService.generatePaymentReceipt(payment.getId());

        // then
        assertThat(receipt).contains(date.toString());
    }

    @Test
    void should_haveEveryLineOfSameWidth_when_generatePaymentReceipt() {
        // given
        Payment payment = PaymentFactory.createProperPaymentCase1();
        LocalDate date = LocalDate.of(2015, 7, 3);
        payment.setDate(date);
        int seatsCount = 4;
        BigDecimal pricePerSeat = BigDecimal.valueOf(14.37);
        mockDataForReceipt(payment, seatsCount, pricePerSeat);

        // when
        String receipt = paymentService.generatePaymentReceipt(payment.getId());

        // then
        String[] lines = receipt.split("\n");
        boolean areAllLinesOfEqualLengthOrEmpty =
                Stream.of(lines)
                        .map(String::length)
                        .filter(size -> size != 0)
                        .distinct()
                        .count() == 1;
        assertThat(areAllLinesOfEqualLengthOrEmpty).isTrue();
    }

    private void mockDataForReceipt(Payment payment, int seatsCount, BigDecimal pricePerSeat) {
        Reservation reservation = mock(Reservation.class);
        FootballMatch footballMatch = mock(FootballMatch.class);
        Set<Seat> seats = Stream.generate(Seat::new)
                .limit(seatsCount)
                .collect(Collectors.toSet());
        payment.setReservation(reservation);

        Mockito.when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));
        Mockito.when(reservation.getSeats()).thenReturn(seats);
        Mockito.when(reservation.getFootballMatch()).thenReturn(footballMatch);
        Mockito.when(footballMatch.getPricePerSeat()).thenReturn(pricePerSeat);
    }

    private int countSubstringOccurrences(String input, String substring) {
        return input.split(substring, -1).length - 1;
    }
}