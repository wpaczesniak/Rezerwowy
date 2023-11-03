package com.example.rezerwowy.exceptions;

public class RoleAlreadyExistsException extends RuntimeException {
    public RoleAlreadyExistsException(String name) {
        super(String.format("Role with name %s already exists", name));
    }
}
