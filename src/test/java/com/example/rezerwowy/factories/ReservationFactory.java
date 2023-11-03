package com.example.rezerwowy.factories;

import com.example.rezerwowy.models.Reservation;

public class ReservationFactory {
	private static Long startId = 99L;
	public static Reservation createProperReservationCase1() {
		return Reservation.builder()
				.id(startId++)
				.build();
	}
}
