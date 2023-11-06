package com.example.rezerwowy.exceptions;

public class StadiumAlreadyExistsException extends RuntimeException {
    public StadiumAlreadyExistsException(Long id) {
        super(String.format("Stadium with id: %d already exists", id));
    }
}
