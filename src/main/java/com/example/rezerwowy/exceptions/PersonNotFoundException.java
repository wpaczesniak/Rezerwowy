package com.example.rezerwowy.exceptions;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(Long id) {
        super(String.format("Payment with id: %d not found", id));
    }
}
