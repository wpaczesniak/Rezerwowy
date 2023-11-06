package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.DuplicatePersonIdException;
import com.example.rezerwowy.exceptions.PersonNotFoundException;
import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional
    public Person addPerson(@Valid Person person) {
        if (person.getId() != null && personRepository.existsById(person.getId()))
            throw new DuplicatePersonIdException(person.getId());
        return personRepository.save(person);
    }

    @Transactional
    public Person getPersonById(Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException(personId));
    }

    @Transactional
    public void deletePaymentById(Long personId) {
        if (!personRepository.existsById(personId))
            throw new PersonNotFoundException(personId);
        personRepository.deleteById(personId);
    }
}
