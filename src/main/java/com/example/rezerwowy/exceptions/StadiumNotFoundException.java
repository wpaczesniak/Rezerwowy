package com.example.rezerwowy.exceptions;

public class StadiumNotFoundException extends RuntimeException {
    public StadiumNotFoundException(Long id) {
        super(String.format("Stadium with id: %d not found", id));
    }
}
