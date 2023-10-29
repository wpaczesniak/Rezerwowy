package com.example.rezerwowy.models;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Team {
    @Id
    private Long id;

    @OneToMany
    private List<Person> players;
}
