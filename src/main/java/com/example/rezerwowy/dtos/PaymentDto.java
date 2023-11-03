package com.example.rezerwowy.dtos;

import com.example.rezerwowy.models.Buyer;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record PaymentDto(
        Long id,
        UUID publicId,
        Buyer buyer,
        LocalDate date,
        Long reservationId
) { }
