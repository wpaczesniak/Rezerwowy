package com.example.rezerwowy.exceptions;

public class PaymentAlreadyExistsException extends RuntimeException {
    public PaymentAlreadyExistsException(Long id) {
        super(String.format("Payment with id: %d already exists", id));
    }
}
