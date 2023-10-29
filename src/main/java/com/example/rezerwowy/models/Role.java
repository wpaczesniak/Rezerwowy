package com.example.rezerwowy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Role {
    @Id
    @Column(name="roleID")
    private Long id;

    @ManyToOne
    private Person person;
}
