package com.example.rezerwowy.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TeamTest {
    private static final int MIN_NAME_LENGTH = 5;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_ABBREVIATION_LENGTH = 3;
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
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();

        // when
        team.setName(null);

        // then
        Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
        assertThat(constraintViolations).hasSize(1);
    }

    @Test
    void should_notValidate_when_nameIsEmpty() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();

        // when
        team.setName("");

        // then
        Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
        assertThat(constraintViolations).hasSize(2);
    }

    @Test
    void should_notValidate_when_nameIsShorterThanMinNameLength() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();

        String name = "a".repeat(MIN_NAME_LENGTH - 1);

        // when
        team.setName(name);

        // then
        Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
        assertThat(constraintViolations).hasSize(1);
    }

    @Test
    void should_validate_when_nameEqualsMinNameLength() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();

        String name = "a".repeat(MIN_NAME_LENGTH);

        // when
        team.setName(name);

        // then
        Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    void should_notValidate_when_nameIsLongerThanMaxNameLength() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();

        String name = "a".repeat(MAX_NAME_LENGTH + 1);

        // when
        team.setName(name);

        // then
        Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
        assertThat(constraintViolations).hasSize(1);
    }

    @Test
    void should_validate_when_nameEqualsMaxNameLength() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();

        String name = "a".repeat(MAX_NAME_LENGTH);

        // when
        team.setName(name);

        // then
        Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
        assertThat(constraintViolations).isEmpty();
    }


    @Test
    void should_notValidate_when_abbreviationIsNull() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();

        // when
        team.setAbbreviation(null);

        // then
        Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
        assertThat(constraintViolations).hasSize(1);
    }

    @Test
    void should_notValidate_when_abbreviationIsEmpty() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();

        // when
        team.setAbbreviation("");

        // then
        Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
        assertThat(constraintViolations).hasSize(1);
    }

    @Test
    void should_notValidate_when_abbreviationIsLongerThanMaxAbbreviationLength() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();

        String abbreviation = "a".repeat(MAX_ABBREVIATION_LENGTH + 1);

        // when
        team.setAbbreviation(abbreviation);

        // then
        Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
        assertThat(constraintViolations).hasSize(1);
    }

    @Test
    void should_validate_when_abbreviationEqualsMaxAbbreviationLength() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();

        String abbreviation = "a".repeat(MAX_ABBREVIATION_LENGTH);

        // when
        team.setAbbreviation(abbreviation);

        // then
        Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);
        assertThat(constraintViolations).isEmpty();
    }

}
