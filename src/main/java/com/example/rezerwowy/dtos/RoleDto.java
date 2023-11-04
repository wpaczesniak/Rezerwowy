package com.example.rezerwowy.dtos;

import lombok.Builder;

import java.util.Set;

@Builder
public record RoleDto(
        Long id,
        String name,
        Set<Long> roleOwnersIds
) {
}
