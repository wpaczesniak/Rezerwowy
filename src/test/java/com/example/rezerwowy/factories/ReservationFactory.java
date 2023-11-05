package com.example.rezerwowy.factories;

import com.example.rezerwowy.models.Reservation;

public class ReservationFactory {
	private static Long startId = 99L;
	public static Reservation createProperReservationCase1() {
		return Reservation.builder()
				.comment("seat near the pitch")
				.id(startId++)
				.build();
	}

	public static Reservation createProperReservationCase2() {
		return Reservation.builder()
				.comment("seat in the middle")
				.id(startId++)
				.build();
	}

	public static Reservation createProperReservationCase3() {
		return Reservation.builder()
				.comment("seat in the corner")
				.id(startId++)
				.build();
	}
}
