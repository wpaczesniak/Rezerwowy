package com.example.rezerwowy.models;

import com.example.rezerwowy.factories.BuyerFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BuyerTest {
    private static Validator validator;

    @BeforeAll
    static void init() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
	void should_notValidate_when_nameIsNull() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase1();

        // when
        buyer.setName(null);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
	void should_notValidate_when_nameIsEmpty() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase2();

        // when
        buyer.setName("");

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
	void should_validate_when_nameIs3CharsLong() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase1();
        String name = "Jan";

        // when
        buyer.setName(name);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
	void should_validate_when_nameIs9CharsLong() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase3();
        String name = "Krzysztof";

        // when
        buyer.setName(name);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
	void should_validate_when_nameContainsUnicodeChars() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase3();
        String name = "Mściwój";

        // when
        buyer.setName(name);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
	void should_notValidate_when_nameIs70CharsLong() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase1();
        String name = "Alicja Ewa".repeat(7);

        // when
        buyer.setName(name);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
	void should_notValidate_when_surnameIsNull() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase2();

        // when
        buyer.setSurname(null);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
	void should_validate_when_surnameIs5CharsLong() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase2();
        String surname = "Nowak";

        // when
        buyer.setName(surname);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
	void should_notValidate_when_surnameIs80CharsLong() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase1();
        String surname = "RICHARDSON".repeat(8);

        // when
        buyer.setSurname(surname);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
	void should_notValidate_when_emailIsNull() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase2();

        // when
        buyer.setEmail(null);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
	void should_validate_when_emailIsProper() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase2();
        String email = "john123@mail.com";

        // when
        buyer.setEmail(email);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
	void should_notValidate_when_emailHasWrongFormat() {
        // given
        Buyer buyer = BuyerFactory.createProperBuyerCase2();
        String email = "www.my.page.com";

        // when
        buyer.setEmail(email);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }
}