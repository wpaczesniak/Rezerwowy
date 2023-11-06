package com.example.rezerwowy.exceptions;

public class TeamAlreadyExistsException extends RuntimeException {
    public TeamAlreadyExistsException(String name) {
        super(String.format("Team with name %s already exists", name));
    }
}
