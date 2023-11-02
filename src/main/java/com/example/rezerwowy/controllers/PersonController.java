package com.example.rezerwowy.controllers;

import com.example.rezerwowy.dtos.PersonDTO;
import com.example.rezerwowy.mappers.PersonMapper;
import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.services.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("people")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;

    @PostMapping
    public ResponseEntity<PersonDTO> addPerson(@RequestBody @Valid PersonDTO personDTO) {

        Person personToCreate = personMapper.mapPersonDTOToPerson(personDTO);
        Person addedPerson = personService.addPerson(personToCreate);
        PersonDTO createdPersonDTO = personMapper.mapPersonToPersonDTO(addedPerson);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable("id") Long personId) {

        Person person = personService.getPersonById(personId);
        PersonDTO personDTO = personMapper.mapPersonToPersonDTO(person);

        return ResponseEntity.status(HttpStatus.OK).body(personDTO);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonById(@PathVariable("id") Long personId) {
        personService.deletePaymentById(personId);
        return ResponseEntity.ok().build();
    }
}
