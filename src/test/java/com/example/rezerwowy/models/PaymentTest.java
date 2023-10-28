package com.example.rezerwowy.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {
    private static Validator validator;
    @BeforeAll
    static void init() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    public void should_notValidate_when_dateIsNull() {
        // given
        Payment payment = createProperPaymentCase1();

        // when
        payment.setDate(null);

        // then
        Set<ConstraintViolation<Payment>> constraintViolations =
                validator.validate(payment);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_validate_when_dateIsInThePast() {
        // given
        Payment payment = createProperPaymentCase1();
        LocalDate date = LocalDate.now().minusYears(1);

        // when
        payment.setDate(date);

        // then
        Set<ConstraintViolation<Payment>> constraintViolations =
                validator.validate(payment);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_validate_when_dateIsInToday() {
        // given
        Payment payment = createProperPaymentCase3();
        LocalDate date = LocalDate.now();

        // when
        payment.setDate(date);

        // then
        Set<ConstraintViolation<Payment>> constraintViolations =
                validator.validate(payment);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_notValidate_when_dateIsInTheFuture() {
        // given
        Payment payment = createProperPaymentCase1();
        LocalDate date = LocalDate.now().plusYears(1).plusDays(4);

        // when
        payment.setDate(date);

        // then
        Set<ConstraintViolation<Payment>> constraintViolations =
                validator.validate(payment);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_notValidate_when_buyerIsNull() {
        // given
        Payment payment = createProperPaymentCase1();

        // when
        payment.setBuyer(null);

        // then
        Set<ConstraintViolation<Payment>> constraintViolations =
                validator.validate(payment);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_validate_when_paymentIsProper() {
        // given
        Payment payment = createProperPaymentCase2();

        // when then
        Set<ConstraintViolation<Payment>> constraintViolations =
                validator.validate(payment);
        assertThat(constraintViolations).isEmpty();
    }

    static Payment createProperPaymentCase1() {
        return Payment.builder()
                .buyer(BuyerTest.createProperBuyerCase1())
                .date(LocalDate.of(2020, 4, 15))
                .build();
    }

    static Payment createProperPaymentCase2() {
        return Payment.builder()
                .buyer(BuyerTest.createProperBuyerCase2())
                .date(LocalDate.of(2022, 11, 5))
                .build();
    }

    static Payment createProperPaymentCase3() {
        return Payment.builder()
                .buyer(BuyerTest.createProperBuyerCase3())
                .date(LocalDate.of(2023, 8, 6))
                .build();
    }

}