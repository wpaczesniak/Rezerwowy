package com.example.rezerwowy.models;

import com.example.rezerwowy.factories.FootballMatchFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FootballMatchTest {
    private static Validator validator;

    @BeforeAll
    static void init() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }
    }

    @Test
    void should_notValidate_when_dateIsNull() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();

        // when
        match.setDate(null);

        // then
        Set<ConstraintViolation<FootballMatch>> constraintViolations =
                validator.validate(match);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    void should_notValidate_when_hostTeamIsNull() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();

        // when
        match.setHostTeamId(null);

        // then
        Set<ConstraintViolation<FootballMatch>> constraintViolations =
                validator.validate(match);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    void should_notValidate_when_guestTeamIsNull() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();

        // when
        match.setGuestTeamId(null);

        // then
        Set<ConstraintViolation<FootballMatch>> constraintViolations =
                validator.validate(match);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    void should_notValidate_when_stadiumIsNull() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();

        // when
        match.setStadiumId(null);

        // then
        Set<ConstraintViolation<FootballMatch>> constraintViolations =
                validator.validate(match);
        assertThat(constraintViolations).isNotEmpty();
    }
}
