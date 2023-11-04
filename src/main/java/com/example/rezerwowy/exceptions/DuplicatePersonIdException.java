package com.example.rezerwowy.exceptions;

public class DuplicatePersonIdException extends RuntimeException{
    public DuplicatePersonIdException(Long id) {
        super(String.format("Person with given id: %d already exists", id));
    }
}
