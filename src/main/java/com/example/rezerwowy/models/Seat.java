package com.example.rezerwowy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Seat {
    @Id
    Long id;
    // TODO
}