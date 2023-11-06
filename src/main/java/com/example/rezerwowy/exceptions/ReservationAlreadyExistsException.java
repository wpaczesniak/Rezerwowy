package com.example.rezerwowy.exceptions;

public class ReservationAlreadyExistsException extends RuntimeException {
	public ReservationAlreadyExistsException(Long id) {
		super(String.format("Reservation with id: %d already exists", id));
	}
}
