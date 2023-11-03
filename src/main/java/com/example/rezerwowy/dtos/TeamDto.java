package com.example.rezerwowy.dtos;

import lombok.Builder;

@Builder
public record TeamDto(
        Long id,
        String name,
        String abbreviation
) {
}
