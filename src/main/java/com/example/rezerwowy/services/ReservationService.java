package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.ReservationAlreadyExistsException;
import com.example.rezerwowy.exceptions.ReservationNotFoundException;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.repositories.ReservationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;

	@Transactional
	public Reservation createReservation(@Valid Reservation reservation) {
		if (reservation.getId() != null && reservationRepository.existsById(reservation.getId()))
			throw new ReservationAlreadyExistsException(reservation.getId());
		return reservationRepository.save(reservation);
	}

	@Transactional
	public Reservation getReservationById(Long reservationId) {
		return reservationRepository.findById(reservationId)
				.orElseThrow(() -> new ReservationNotFoundException(reservationId));
	}

	@Transactional
	public void deleteReservationById(Long reservationId) {
		if (!reservationRepository.existsById(reservationId))
			throw new ReservationNotFoundException(reservationId);
		reservationRepository.deleteById(reservationId);
	}
}
