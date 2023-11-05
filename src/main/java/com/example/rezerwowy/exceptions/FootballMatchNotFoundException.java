package com.example.rezerwowy.exceptions;

import java.util.UUID;

public class FootballMatchNotFoundException extends RuntimeException {
    public FootballMatchNotFoundException(Long id) { super(String.format("Football match with id: %d not found", id));
    }

}
