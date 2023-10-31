package com.example.rezerwowy.controllers;

import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("people")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        try {
            personService.addPerson(person);
            return ResponseEntity.ok(person);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/teams/{id}}")
    public ResponseEntity<Person> addPerson(@PathVariable("id") Long teamId, @RequestBody Person person) {
        try {
            if (teamId != null) {
                Team team = Team.builder().build(); // to do
                person.setTeam(team);
            }
            Person addedPerson = personService.addPerson(person);
            return ResponseEntity.ok(addedPerson);
        }
        catch (Exception e) {
            return  ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") Long personId) {
        try {
            Person person = personService.getPersonById(personId);
            return ResponseEntity.status(HttpStatus.OK).body(person);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deletePersonById(@PathVariable("id") Long personId) {
        try {
            personService.deletePaymentById(personId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
