package com.example.rezerwowy.dtos;

import java.util.Set;

import com.example.rezerwowy.models.Stadium;

import lombok.Builder;

@Builder
public record SeatDto(
    Long id,
    Long seatNumber,
    Long stadiumId,
    Long reservationId
) { }
