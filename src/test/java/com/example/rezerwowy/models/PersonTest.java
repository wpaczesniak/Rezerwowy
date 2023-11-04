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
    public void should_Validate_when_nameSizeIsGood() {
        // given
        Person person = PersonFactory.createPersonCase1();
        String name = "Karol";

        // when
        person.setName(name);

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_notValidate_when_nameIsNull() {
        // given
        Person person = PersonFactory.createPersonCase1();

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
        Person person = PersonFactory.createPersonCase2();

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
        Person person = PersonFactory.createPersonCase2();

        // when
        person.setName("Erling Braut");

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_notValidate_when_nameIsTooLong() {
        // given
        Person person = PersonFactory.createPersonCase1();
        String name = person.getName().repeat(100);

        // when
        person.setName(name);

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_notValidate_when_nameIsTooShort() {
        // given
        Person person = PersonFactory.createPersonCase1();
        String name ="X";

        // when
        person.setName(name);

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_notValidate_when_nameIsNotLetters() {
        // given
        Person person = PersonFactory.createPersonCase1();
        String name ="&!?";

        // when
        person.setName(name);

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_notValidate_when_nameIsNumeric() {
        // given
        Person person = PersonFactory.createPersonCase1();
        String name ="54288";

        // when
        person.setName(name);

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_notValidate_when_nameIsNickWithNotLetter() {
        // given
        Person person = PersonFactory.createPersonCase1();
        String name ="Kardo93442";

        // when
        person.setName(name);

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_Validate_when_surnameSizeIsGood() {
        // given
        Person person = PersonFactory.createPersonCase1();
        String surname = "Do";

        // when
        person.setSurname(surname);

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_notValidate_when_surnameIsNull() {
        // given
        Person person = PersonFactory.createPersonCase3();

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
        Person person = PersonFactory.createPersonCase2();

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
        Person person = PersonFactory.createPersonCase4();

        // when
        person.setSurname("De Bruyne");

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    public void should_notValidate_when_surnameIsTooLong() {
        // given
        Person person = PersonFactory.createPersonCase1();
        String surname = person.getSurname().repeat(100);

        // when
        person.setSurname(surname);

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isNotEmpty();
    }

    @Test
    public void should_notValidate_when_surnameIsTooShort() {
        // given
        Person person = PersonFactory.createPersonCase1();
        String surname = "X";

        // when
        person.setSurname(surname);

        // then
        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);
        assertThat(constraintViolations).isNotEmpty();
    }
}
