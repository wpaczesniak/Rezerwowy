package com.example.rezerwowy.repositories;

import com.example.rezerwowy.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    public Optional<Payment> findByPublicId(UUID publicId);
}
