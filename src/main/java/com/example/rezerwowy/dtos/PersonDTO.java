package com.example.rezerwowy.dtos;

import lombok.Builder;

@Builder
public record PersonDTO(
        Long id,
        String name,
        String surname,
        Long teamId,
        Long roleId
) {
}
