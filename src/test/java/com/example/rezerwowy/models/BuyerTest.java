package com.example.rezerwowy.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class BuyerTest {
    private static Validator validator;

    @BeforeAll
    static void init() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    public void should_notValidate_when_nameIsNull() {
        // given
        Buyer buyer = createProperBuyerCase1();

        // when
        buyer.setName(null);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_notValidate_when_nameIsEmpty() {
        // given
        Buyer buyer = createProperBuyerCase2();

        // when
        buyer.setName("");

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_validate_when_nameIs3CharsLong() {
        // given
        Buyer buyer = createProperBuyerCase1();
        String name = "Jan";

        // when
        buyer.setName(name);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_validate_when_nameIs9CharsLong() {
        // given
        Buyer buyer = createProperBuyerCase3();
        String name = "Krzysztof";

        // when
        buyer.setName(name);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_validate_when_nameContainsUnicodeChars() {
        // given
        Buyer buyer = createProperBuyerCase3();
        String name = "Mściwój";

        // when
        buyer.setName(name);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_notValidate_when_nameIs70CharsLong() {
        // given
        Buyer buyer = createProperBuyerCase1();
        String name = "Alicja Ewa".repeat(7);

        // when
        buyer.setName(name);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_notValidate_when_surnameIsNull() {
        // given
        Buyer buyer = createProperBuyerCase2();

        // when
        buyer.setSurname(null);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_validate_when_surnameIs5CharsLong() {
        // given
        Buyer buyer = createProperBuyerCase2();
        String surname = "Nowak";

        // when
        buyer.setName(surname);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_notValidate_when_surnameIs80CharsLong() {
        // given
        Buyer buyer = createProperBuyerCase1();
        String surname = "RICHARDSON".repeat(8);

        // when
        buyer.setSurname(surname);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_notValidate_when_emailIsNull() {
        // given
        Buyer buyer = createProperBuyerCase2();

        // when
        buyer.setEmail(null);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_validate_when_emailIsProper() {
        // given
        Buyer buyer = createProperBuyerCase2();
        String email = "john123@mail.com";

        // when
        buyer.setEmail(email);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_notValidate_when_emailHasWrongFormat() {
        // given
        Buyer buyer = createProperBuyerCase2();
        String email = "www.my.page.com";

        // when
        buyer.setEmail(email);

        // then
        Set<ConstraintViolation<Buyer>> constraintViolations =
                validator.validate(buyer);
        assertThat(constraintViolations).isNotEmpty();
    }

    static Buyer createProperBuyerCase1() {
        return Buyer.builder()
                .name("Jan")
                .surname("Nowak")
                .email("jan-nowak@gmail.com")
                .build();
    }

    static Buyer createProperBuyerCase2() {
        return Buyer.builder()
                .name("Anna")
                .surname("Kowalska-Nowak")
                .email("anna321@outlook.com")
                .build();
    }

    static Buyer createProperBuyerCase3() {
        return Buyer.builder()
                .name("Jorja")
                .surname("Harmon Garner")
                .email("caitie2best7stokes.9@mail.com")
                .build();
    }
}