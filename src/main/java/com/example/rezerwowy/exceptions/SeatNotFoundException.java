package com.example.rezerwowy.exceptions;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException(Long id) {
        super(String.format("Seat with id: %d not found", id));
    }
}

