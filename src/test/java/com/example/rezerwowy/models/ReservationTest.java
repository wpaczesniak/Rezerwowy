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
	void should_notValidate_when_commentIsNull() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		// when
		reservation.setComment(null);
		// then
		assertThat(validator.validate(reservation)).isNotEmpty();
	}
	@Test
	void should_validate_when_commentIsEmpty() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		// when
		reservation.setComment("");
		// then
		assertThat(validator.validate(reservation)).isEmpty();
	}

	@Test
	void should_validate_when_commentIs1CharLong() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		String comment = "a";
		// when
		reservation.setComment(comment);
		// then
		assertThat(validator.validate(reservation)).isEmpty();
	}
	@Test
	void should_notValidate_when_commentIs946CharsLong() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		String comment = "Jak to jest być skrybą, dobrze? A, wie pan, moim zdaniem to nie ma tak," +
				"że dobrze, albo że niedobrze. Gdybym miał powiedzieć, co cenię w życiu najbardziej," +
				"powiedziałbym, że ludzi. Ludzi, którzy podali mi pomocną dłoń, kiedy sobie nie radziłem," +
				"kiedy byłem sam, i co ciekawe, to właśnie przypadkowe spotkania wpływają na nasze życie." +
				"Chodzi o to, że kiedy wyznaje się pewne wartości, nawet pozornie uniwersalne, bywa, że nie" +
				"znajduje się zrozumienia, które by tak rzec, które pomaga się nam rozwijać. Ja miałem szczęście," +
				"by tak rzec, ponieważ je znalazłem, i dziękuję życiu! Dziękuję mu; życie to śpiew, życie to taniec," +
				"życie to miłość! Wielu ludzi pyta mnie o to samo: ale jak ty to robisz, skąd czerpiesz tę radość?" +
				"A ja odpowiadam, że to proste! To umiłowanie życia. To właśnie ono sprawia, że dzisiaj na przykład" +
				"buduję maszyny, a jutro – kto wie? Dlaczego by nie – oddam się pracy społecznej i będę, ot, choćby," +
				"sadzić... doć— m-marchew...";
		// when
		reservation.setComment(comment);
		// then
		assertThat(validator.validate(reservation)).isNotEmpty();
	}
	@Test
	void should_validate_when_commentIs500CharsLong() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		String comment = "a".repeat(500);
		// when
		reservation.setComment(comment);
		// then
		assertThat(validator.validate(reservation)).isEmpty();
	}
	@Test
	void should_notValidate_when_commentIs501CharsLong() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		String comment = "a".repeat(501);
		// when
		reservation.setComment(comment);
		// then
		assertThat(validator.validate(reservation)).isNotEmpty();
	}

	@Test
	void should_validate_when_commentContainsUnicodeChars() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		String comment = "ąćęłńóśźż";
		// when
		reservation.setComment(comment);
		// then
		assertThat(validator.validate(reservation)).isEmpty();
	}
	@Test
	void should_validate_when_commentContainsDigits() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		String comment = "0123456789";
		// when
		reservation.setComment(comment);
		// then
		assertThat(validator.validate(reservation)).isEmpty();
	}
	@Test
	void should_validate_when_commentContainsSpecialChars() {
		// given
		Reservation reservation = ReservationFactory.createProperReservationCase1();
		String comment = "!@#$%^&*()_+{}[]:;\"'\\|<>?/";
		// when
		reservation.setComment(comment);
		// then
		assertThat(validator.validate(reservation)).isEmpty();
	}
}