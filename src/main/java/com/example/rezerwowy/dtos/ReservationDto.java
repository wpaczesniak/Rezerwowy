package com.example.rezerwowy.dtos;

import com.example.rezerwowy.models.Payment;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record ReservationDto(
		Long id,
		UUID publicId,
		String comment,
		Set<Long> seatIds,
		Long footballMatchId,
		Long paymentId
) { }
