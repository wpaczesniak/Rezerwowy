package com.example.rezerwowy.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTest {
    private static final int MAX_NAME_LENGTH = 20;
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
        Role role = Role.builder()
                .id(100L)
                .name("Trainer")
                .build();

        // when
        role.setName(null);

        // then
        Set<ConstraintViolation<Role>> constraintViolations = validator.validate(role);
        assertThat(constraintViolations).hasSize(1);
    }

    @Test
    void should_notValidate_when_nameIsEmpty() {
        // given
        Role role = Role.builder()
                .id(100L)
                .name("Trainer")
                .build();

        // when
        role.setName("");

        // then
        Set<ConstraintViolation<Role>> constraintViolations = validator.validate(role);
        assertThat(constraintViolations).hasSize(1);
    }

    @Test
    void should_notValidate_when_nameIsLongerThanMaxNameLength() {
        // given
        Role role = Role.builder()
                .id(100L)
                .name("Trainer")
                .build();

        String name = "a".repeat(MAX_NAME_LENGTH + 1);

        // when
        role.setName(name);

        // then
        Set<ConstraintViolation<Role>> constraintViolations = validator.validate(role);
        assertThat(constraintViolations).hasSize(1);
    }

    @Test
    void should_validate_when_nameEqualsMaxNameLength() {
        // given
        Role role = Role.builder()
                .id(100L)
                .name("Trainer")
                .build();

        String name = "a".repeat(MAX_NAME_LENGTH);

        // when
        role.setName(name);

        // then
        Set<ConstraintViolation<Role>> constraintViolations = validator.validate(role);
        assertThat(constraintViolations).isEmpty();
    }

}
