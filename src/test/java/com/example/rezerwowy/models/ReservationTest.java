package com.example.rezerwowy.models;

import com.example.rezerwowy.factories.ReservationFactory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class ReservationTest {
	private static Validator validator;
	@BeforeAll
	static void init() {
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			validator = factory.getValidator();
		}
	}
	// write unit tests for Reservation class here
	@Test
	public void startId_shouldBe99() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		// when
		// then
		assertThat(reservation.getId()).isEqualTo(99L);
	}
}
