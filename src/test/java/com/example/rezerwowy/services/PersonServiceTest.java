package com.example.rezerwowy.services;

import com.example.rezerwowy.factories.PersonFactory;
import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    private PersonService personService;

    @BeforeEach
    void setup() {
        personService = new PersonService(personRepository);
    }

    @Test
    public void should_callAppropriateMethodInRepository_when_addPerson() {
        //given
        Person person = PersonFactory.createPersonCase1();
        Mockito.when(personRepository.save(person)).thenReturn(person);

        // when
        personService.addPerson(person);

        // then
        Mockito.verify(personRepository).save(person);
    }

    @Test
    public void should_callAppropriateMethodInRepository_when_getPersonById() {
        //given
        Person person = PersonFactory.createPersonCase2();
        Long id = person.getId();
        Mockito.when(personRepository.findById(id)).thenReturn(Optional.of(person));

        // when
        personService.getPersonById(id);

        // then
        Mockito.verify(personRepository).findById(id);
    }

    @Test
    public void should_callAppropriateMethodInRepository_when_deletePersonById() {
        //given
        Person person = PersonFactory.createPersonCase3();
        Long id = person.getId();
        Mockito.when(personRepository.existsById(id)).thenReturn(true);

        // when
        personService.deletePaymentById(id);

        // then
        Mockito.verify(personRepository).deleteById(id);
    }
}
