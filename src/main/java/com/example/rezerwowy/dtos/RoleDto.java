package com.example.rezerwowy.dtos;

import lombok.Builder;

@Builder
public record RoleDto(
        Long id,
        String name
) {
}
