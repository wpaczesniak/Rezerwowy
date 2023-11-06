package com.example.rezerwowy.controllers;

import com.example.rezerwowy.dtos.ReservationDto;
import com.example.rezerwowy.mappers.ReservationMapper;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;
	private final ReservationMapper reservationMapper;

	@PostMapping
	public ResponseEntity<ReservationDto> createReservation(@RequestBody @Valid ReservationDto reservationDtoToCreate) {
		Reservation reservationToCreate = reservationMapper.mapReservationDtoToReservation(reservationDtoToCreate);
		Reservation createdReservation = reservationService.createReservation(reservationToCreate);
		ReservationDto createdReservationDto = reservationMapper.mapReservationToReservationDto(createdReservation);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdReservationDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReservationDto> getReservationById(@PathVariable("id") Long reservationId) {
		Reservation reservation = reservationService.getReservationById(reservationId);
		ReservationDto reservationDto = reservationMapper.mapReservationToReservationDto(reservation);

		return ResponseEntity.status(HttpStatus.OK).body(reservationDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReservationById(@PathVariable("id") Long reservationId) {
		reservationService.deleteReservationById(reservationId);

		return ResponseEntity.ok().build();
	}

}
