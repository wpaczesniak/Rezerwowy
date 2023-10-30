package com.example.rezerwowy.models;

import com.example.rezerwowy.factories.PersonFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonTest {
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
        Person person = PersonFactory.createProperBuyerCase1();

        // when
        person.setName(null);

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_notValidate_when_nameIsEmpty() {
        // given
        Person person = PersonFactory.createProperBuyerCase2();

        // when
        person.setName("");

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_Validate_when_manyNames() {
        // given
        Person person = PersonFactory.createProperBuyerCase2();

        // when
        person.setName("Erling Braut");

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_notValidate_when_surnameIsNull() {
        // given
        Person person = PersonFactory.createProperBuyerCase3();

        // when
        person.setSurname(null);

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_notValidate_when_surnameIsEmpty() {
        // given
        Person person = PersonFactory.createProperBuyerCase2();

        // when
        person.setSurname("");

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_Validate_when_manySurnames() {
        // given
        Person person = PersonFactory.createProperBuyerCase2();

        // when
        person.setSurname("De Bruyne");

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isEmpty();
    }
}
