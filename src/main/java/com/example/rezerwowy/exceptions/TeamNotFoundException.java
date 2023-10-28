package com.example.rezerwowy.exceptions;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Long id) {
        super(String.format("Team with id: %d not found", id));
    }

}
