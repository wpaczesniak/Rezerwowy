package com.example.rezerwowy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Team {
    @Id
    private Long id;

    @OneToMany
    private List<Person> players;
}
