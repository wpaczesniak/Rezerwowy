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

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment) {
        try {
            paymentService.addPayment(payment);
        }
        catch (Exception e) {
            return  ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(payment);
    }

    @PostMapping("/reservationId")
    public ResponseEntity<Payment> addPayment(@PathVariable("id") Long reservationId, @RequestBody Payment payment) {
        try {
            if (reservationId != null) {
                Reservation foundReservation = Reservation.builder().build(); // TODO
                payment.setReservation(foundReservation);
            }
            paymentService.addPayment(payment);
        }
        catch (Exception e) {
            return  ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Long paymentId) {
        Payment payment = null;
        try {
             payment = paymentService.getPaymentById(paymentId);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(payment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable("id") Long paymentId) {
        try {
            paymentService.deletePaymentById(paymentId);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<Payment> getPaymentByPublicId(@PathVariable("id") UUID paymentPublicId) {
        Payment payment;
        try {
            payment = paymentService.getPaymentByPublicId(paymentPublicId);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(payment);
    }

    @DeleteMapping("/public/{id}")
    public ResponseEntity<Void> deletePaymentByPublicId(@PathVariable("id") UUID paymentPublicId) {
        try {
            paymentService.deletePaymentByPublicId(paymentPublicId);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/receipt/{id}")
    public ResponseEntity<String> generatePaymentReceipt(@PathVariable("id") Long paymentId) {
        String receipt;
        try {
            receipt = paymentService.generatePaymentReceipt(paymentId);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(receipt);
    }
}
