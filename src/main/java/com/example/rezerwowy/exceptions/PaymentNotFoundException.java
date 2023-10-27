package com.example.rezerwowy.exceptions;

import java.util.UUID;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(Long id) {
        super(String.format("Payment with id: %d not found", id));
    }

    public PaymentNotFoundException(UUID publicId) {
        super(String.format("Payment with id: %s not found", publicId));
    }
}
