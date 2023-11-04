package com.example.rezerwowy.integration;

import com.example.rezerwowy.dtos.PersonDTO;
import com.example.rezerwowy.factories.PersonFactory;
import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.repositories.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class PersonIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DirtiesContext
    public void should_successfullyCreatePerson_when_createPerson() {
        // given
        Person person = PersonFactory.createPersonCase4();
        person.setId(null);

        // when
        ResponseEntity<PersonDTO> createResponse = restTemplate
                .postForEntity("/people", person, PersonDTO.class);

        // then
        Assertions.assertAll(
                () -> assertThat(createResponse.getStatusCode())
                        .isEqualTo(HttpStatus.CREATED),
                () -> assertThat(createResponse.getBody().id())
                        .isNotNull(),
                () -> assertThat(createResponse.getBody().name())
                        .isEqualTo(person.getName()),
                () -> assertThat(createResponse.getBody().surname())
                        .isEqualTo(person.getSurname())
        );
    }

    @Test
    @DirtiesContext
    public void should_deleteInRepository_when_deleteExistingPayment() {
        // given
        Person person = PersonFactory.createPersonCase2();
        person.setId(null);
        Person addedPerson = personRepository.save(person);

        // when
        restTemplate.delete("/people/" + addedPerson.getId());

        //then
        assertThat(personRepository.existsById(person.getId())).isFalse();
    }

    @Test
    @DirtiesContext
    public void should_returnCorrectData_when_getExistingPerson() {
        // given
        Person person = PersonFactory.createPersonCase2();
        person.setId(null);
        Person addedPerson = personRepository.save(person);

        // when
        ResponseEntity<PersonDTO> createResponse = restTemplate
                .getForEntity("/people/" + person.getId(), PersonDTO.class);

        // then
        Assertions.assertAll(
                () -> assertThat(createResponse.getStatusCode())
                        .isEqualTo(HttpStatus.OK),
                () -> assertThat(createResponse.getBody().id())
                        .isNotNull(),
                () -> assertThat(createResponse.getBody().name())
                        .isEqualTo(addedPerson.getName()),
                () -> assertThat(createResponse.getBody().surname())
                        .isEqualTo(addedPerson.getSurname())
        );
    }

    @Test
    @DirtiesContext
    public void should_returnBadRequest_when_PersonIdCollision() {
        // given
        Person person = PersonFactory.createPersonCase3();
        person.setId(null);
        personRepository.save(person);

        // when
        ResponseEntity<PersonDTO> createResponse = restTemplate
                .postForEntity("/people", person, PersonDTO.class);

        // then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext
    public void should_returnBadRequest_when_createPersonWithNullName() {
        // given
        Person person = PersonFactory.createPersonCase4();
        person.setId(null);
        person.setName(null);

        // when
        ResponseEntity<PersonDTO> createResponse = restTemplate
                .postForEntity("/people", person, PersonDTO.class);

        //then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext
    public void should_returnBadRequest_when_createPersonWithEmptyName() {
        // given
        Person person = PersonFactory.createPersonCase3();
        person.setId(null);
        person.setName("");

        // when
        ResponseEntity<PersonDTO> createResponse = restTemplate
                .postForEntity("/people", person, PersonDTO.class);

        //then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext
    public void should_returnNotFound_when_getPersonWithIdThatIsNotPresentInDatabase() {
        // given
        Long personId = 2112023L;

        // when
        ResponseEntity<PersonDTO> createResponse = restTemplate
                .getForEntity("/people/" + personId, PersonDTO.class);

        // then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
