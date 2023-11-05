package com.example.rezerwowy.dtos;

import java.util.Set;

import lombok.Builder;

@Builder
public record StadiumDto(
    Long id,
    String name, 
    String city, 
    int capacity,
    Long matchId,
    Set<Long> seatIds
) { } 