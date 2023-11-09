package com.example.rezerwowy.controllers;

import com.example.rezerwowy.dtos.PaymentDto;
import com.example.rezerwowy.mappers.PaymentMapper;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// wefbkjdsbjbds
@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody @Valid PaymentDto paymentDtoToCreate) {
        Payment paymentToCreate = paymentMapper.mapPaymentDtoToPayment(paymentDtoToCreate);
        Payment createdPayment = paymentService.createPayment(paymentToCreate);
        PaymentDto createdPaymentDto = paymentMapper.mapPaymentToPaymentDto(createdPayment);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaymentDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable("id") Long paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        PaymentDto paymentDto = paymentMapper.mapPaymentToPaymentDto(payment);

        return ResponseEntity.status(HttpStatus.OK).body(paymentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable("id") Long paymentId) {
        paymentService.deletePaymentById(paymentId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/receipt/{id}")
    public ResponseEntity<String> generatePaymentReceipt(@PathVariable("id") Long paymentId) {
        String receipt = paymentService.generatePaymentReceipt(paymentId);

        return ResponseEntity.status(HttpStatus.OK).body(receipt);
    }
}
