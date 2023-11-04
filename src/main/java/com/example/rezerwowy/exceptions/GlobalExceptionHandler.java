package com.example.rezerwowy.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handleException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Void> handleException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(PaymentAlreadyExistsException.class)
    public ResponseEntity<Void> handleException(PaymentAlreadyExistsException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Void> handleException(PaymentNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(FootballMatchAlreadyExistsException.class)
    public ResponseEntity<Void> handleException(FootballMatchAlreadyExistsException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(FootballMatchNotFoundException.class)
    public ResponseEntity<Void> handleException(FootballMatchNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
