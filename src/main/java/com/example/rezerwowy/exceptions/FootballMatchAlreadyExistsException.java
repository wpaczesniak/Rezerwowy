package com.example.rezerwowy.exceptions;

public class FootballMatchAlreadyExistsException extends RuntimeException {
    public FootballMatchAlreadyExistsException() {
        super("Football match already exists");
    }
}
