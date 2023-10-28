package com.example.rezerwowy.controllers;

import com.example.rezerwowy.exceptions.PaymentNotFoundException;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment) {
        try {
            paymentService.addPayment(payment);
            return ResponseEntity.ok(payment);
        }
        catch (Exception e) {
            return  ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/reservationId")
    public ResponseEntity<Payment> addPayment(@PathVariable("id") Long reservationId, @RequestBody Payment payment) {
        try {
            if (reservationId != null) {
                Reservation foundReservation = Reservation.builder().build(); // TODO
                payment.setReservation(foundReservation);
            }
            Payment addedPayment = paymentService.addPayment(payment);
            return ResponseEntity.ok(addedPayment);
        }
        catch (Exception e) {
            return  ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Long paymentId) {
        try {
            Payment payment = paymentService.getPaymentById(paymentId);
            return ResponseEntity.status(HttpStatus.OK).body(payment);
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

    @GetMapping("/public/{id}")
    public ResponseEntity<Payment> getPaymentByPublicId(@PathVariable("id") UUID paymentPublicId) {
        try {
            Payment payment = paymentService.getPaymentByPublicId(paymentPublicId);
            return ResponseEntity.status(HttpStatus.OK).body(payment);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/public/{id}")
    public ResponseEntity<Void> deletePaymentByPublicId(@PathVariable("id") UUID paymentPublicId) {
        try {
            paymentService.deletePaymentByPublicId(paymentPublicId);
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
