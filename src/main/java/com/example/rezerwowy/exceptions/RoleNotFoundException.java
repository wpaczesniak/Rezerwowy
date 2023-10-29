package com.example.rezerwowy.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(Long id) {
        super(String.format("Role with id: %d not found", id));
    }
}
