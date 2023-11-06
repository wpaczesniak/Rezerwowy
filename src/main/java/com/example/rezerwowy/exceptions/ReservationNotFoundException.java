package com.example.rezerwowy.exceptions;

import java.util.UUID;

public class ReservationNotFoundException extends RuntimeException {
	public ReservationNotFoundException(Long id) {
		super(String.format("Reservation with id: %d not found", id));
	}

	public ReservationNotFoundException(UUID publicId) {
		super(String.format("Reservation with public id: %s not found", publicId));
	}
}