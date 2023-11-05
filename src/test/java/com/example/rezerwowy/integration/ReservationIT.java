package com.example.rezerwowy.integration;

import com.example.rezerwowy.dtos.ReservationDto;
import com.example.rezerwowy.exceptions.PaymentNotFoundException;
import com.example.rezerwowy.exceptions.ReservationNotFoundException;
import com.example.rezerwowy.factories.PaymentFactory;
import com.example.rezerwowy.factories.ReservationFactory;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.repositories.ReservationRepository;
import com.example.rezerwowy.services.ReservationService;
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
public class ReservationIT {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ReservationRepository reservationRepository;

	@Test
	@DirtiesContext
	void should_createReservation_when_reservationDoesntExist() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		reservation.setId(null);

		// when
		ResponseEntity<ReservationDto> createResponse = restTemplate
				.postForEntity("/reservations", reservation, ReservationDto.class);

		// then
		Assertions.assertAll(
				() -> assertThat(createResponse.getStatusCode())
						.isEqualTo(HttpStatus.CREATED),
				() -> assertThat(reservationRepository.existsById(createResponse.getBody().id()))
						.isTrue()
		);
	}

	@Test
	@DirtiesContext
	void should_giveResponseWithTheSameData_when_createReservation() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase2();
		reservation.setId(null);

		// when
		ResponseEntity<ReservationDto> createResponse = restTemplate
				.postForEntity("/reservations", reservation, ReservationDto.class);

		// then
		Assertions.assertAll(
				() -> assertThat(createResponse.getStatusCode())
						.isEqualTo(HttpStatus.CREATED),
				() -> assertThat(createResponse.getBody().id())
						.isNotNull(),
				() -> assertThat(createResponse.getBody().comment())
						.isEqualTo(reservation.getComment())
		);
	}

	@Test
	@DirtiesContext
	void should_returnBadRequest_when_createReservationWithTheSameIdAlreadyExists() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase3();
		reservation.setId(null);
		reservationRepository.save(reservation);

		// when
		ResponseEntity<ReservationDto> createResponse = restTemplate
				.postForEntity("/reservations", reservation, ReservationDto.class);

		// then
		assertThat(createResponse.getStatusCode())
				.isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	@DirtiesContext
	void should_returnBadRequest_when_createReservationWhereCommentIsNull() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase3();
		reservation.setId(null);
		reservation.setComment(null);

		// when
		ResponseEntity<ReservationDto> createResponse = restTemplate
				.postForEntity("/reservations", reservation, ReservationDto.class);

		// then
		assertThat(createResponse.getStatusCode())
				.isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	@DirtiesContext
	void should_returnCorrectData_when_getReservation() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		reservation.setId(null);
		Reservation savedReservation = reservationRepository.save(reservation);

		// when
		ResponseEntity<ReservationDto> getResponse = restTemplate
				.getForEntity("/reservations/" + savedReservation.getId(), ReservationDto.class);

		// then
		Assertions.assertAll(
				() -> assertThat(getResponse.getStatusCode())
						.isEqualTo(HttpStatus.OK),
				() -> assertThat(getResponse.getBody().id())
						.isNotNull(),
				() -> assertThat(getResponse.getBody().comment())
						.isEqualTo(reservation.getComment())
		);
	}

	@Test
	@DirtiesContext
	void should_returnNotFound_when_getReservationThatIsNotPresentInDatabase() {
		// given
		Long reservationId = 420L;

		// when
		ResponseEntity<ReservationDto> getResponse = restTemplate
				.getForEntity("/reservations/" + reservationId, ReservationDto.class);

		// then
		assertThat(getResponse.getStatusCode())
				.isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@DirtiesContext
	void should_deleteInRepository_when_deleteExistingReservation() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase3();
		reservation.setId(null);
		Reservation savedReservation = reservationRepository.save(reservation);

		// when
		restTemplate.delete("/reservations/" + savedReservation.getId());

		// then
		assertThat(reservationRepository.existsById(savedReservation.getId()))
				.isFalse();
	}

	@Test
	@DirtiesContext
	void should_throwException_when_deleteNonExistingReservation() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase3();
		reservationRepository.deleteById(reservation.getId());

		// when then
		assertThatThrownBy(() -> reservationService.deleteReservationById(reservation.getId()))
				.isInstanceOf(ReservationNotFoundException.class);
	}

	@Test
	@DirtiesContext
	void should_returnNotFoundCode_when_deleteNonExistingPayment() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase3();
		reservationRepository.deleteById(reservation.getId());

		// when
		ResponseEntity<Void> deleteResponse = restTemplate.exchange("/reservations/" + reservation.getId(), HttpMethod.DELETE, null, Void.class);

		// when
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}