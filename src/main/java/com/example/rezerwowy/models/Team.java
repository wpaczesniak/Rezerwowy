package com.example.rezerwowy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Team {

    @Id
    private Long id;

    private String name;
}
