package com.example.rezerwowy.controllers;

import com.example.rezerwowy.dtos.PaymentDto;
import com.example.rezerwowy.exceptions.PaymentNotFoundException;
import com.example.rezerwowy.mappers.PaymentMapper;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @PostMapping
    public ResponseEntity<PaymentDto> addPayment(@RequestBody @Valid PaymentDto paymentDtoToAdd) {
        try {
            Payment paymentToAdd = paymentMapper.mapPaymentDtoToPayment(paymentDtoToAdd);
            Payment addedPayment = paymentService.addPayment(paymentToAdd);
            PaymentDto addedPaymentDto = paymentMapper.mapPaymentToPaymentDto(addedPayment);
            return ResponseEntity.ok(addedPaymentDto);
        }
        catch (Exception e) {
            return  ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable("id") Long paymentId) {
        try {
            Payment payment = paymentService.getPaymentById(paymentId);
            PaymentDto paymentDto = paymentMapper.mapPaymentToPaymentDto(payment);
            return ResponseEntity.status(HttpStatus.OK).body(paymentDto);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable("id") Long paymentId) {
        try {
            paymentService.deletePaymentById(paymentId);
            return ResponseEntity.ok().build();
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/receipt/{id}")
    public ResponseEntity<String> generatePaymentReceipt(@PathVariable("id") Long paymentId) {
        try {
            String receipt = paymentService.generatePaymentReceipt(paymentId);
            return ResponseEntity.status(HttpStatus.OK).body(receipt);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
