package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.PersonNotFoundException;
import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.models.Role;
import com.example.rezerwowy.repositories.PersonRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person addPerson(@Valid Person person) {
        return personRepository.save(person);
    }

    public Person getPersonById(Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException(personId));
    }

    public void deletePaymentById(Long personId) {
        if (!personRepository.existsById(personId))
            throw new PersonNotFoundException(personId);
        personRepository.deleteById(personId);
    }
}
