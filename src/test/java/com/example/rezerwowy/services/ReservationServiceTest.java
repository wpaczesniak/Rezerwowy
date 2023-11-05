package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.ReservationAlreadyExistsException;
import com.example.rezerwowy.exceptions.ReservationNotFoundException;
import com.example.rezerwowy.factories.ReservationFactory;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

	@Mock
	private ReservationRepository reservationRepository;

	private ReservationService reservationService;

	@BeforeEach
	void setUp() {
		reservationService = new ReservationService(reservationRepository);
	}

	@Test
	void should_callAppropriateMethodInRepository_when_createReservation() {
		//given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);

		// when
		reservationService.createReservation(reservation);

		// then
		Mockito.verify(reservationRepository).save(reservation);
	}

	@Test
	void should_throwException_when_createReservationWithIdThatAlreadyExists() {
		//given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		Mockito.when(reservationRepository.existsById(reservation.getId())).thenReturn(true);

		// when then
		assertThatThrownBy(() -> reservationService.createReservation(reservation))
				.isInstanceOf(ReservationAlreadyExistsException.class);
	}

	@Test
	void should_callAppropriateMethodInRepository_when_getReservationById() {
		//given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		Mockito.when(reservationRepository.findById(reservation.getId())).thenReturn(java.util.Optional.of(reservation));

		// when
		reservationService.getReservationById(reservation.getId());

		// then
		Mockito.verify(reservationRepository).findById(reservation.getId());
	}

	@Test
	void should_throwException_when_getReservationByIdDoesntExist() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase2();
		Long id = reservation.getId();
		Mockito.when(reservationRepository.findById(id)).thenReturn(Optional.empty());

		// when then
		assertThatThrownBy(() -> reservationService.getReservationById(id))
				.isInstanceOf(ReservationNotFoundException.class);
	}

	@Test
	void should_callAppropriateMethodInRepository_when_deleteReservationById() {
		//given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		Long id = reservation.getId();
		Mockito.when(reservationRepository.existsById(id)).thenReturn(true);

		// when
		reservationService.deleteReservationById(id);

		// then
		Mockito.verify(reservationRepository).deleteById(id);
	}

	@Test
	void should_throwException_when_deleteReservationByIdButItDoesntExist() {
		//given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		Long id = reservation.getId();
		Mockito.when(reservationRepository.existsById(id)).thenReturn(false);

		// when then
		assertThatThrownBy(() -> reservationService.deleteReservationById(id))
				.isInstanceOf(ReservationNotFoundException.class);
	}
}