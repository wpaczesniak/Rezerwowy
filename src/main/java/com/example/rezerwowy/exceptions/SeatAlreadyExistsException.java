package com.example.rezerwowy.exceptions;



public class SeatAlreadyExistsException extends RuntimeException {
    public SeatAlreadyExistsException(Long seatId) {
        super(String.format("Seat with id: %d already exists", seatId));
    }

  
}
