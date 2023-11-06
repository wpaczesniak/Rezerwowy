package com.example.rezerwowy.dtos;

import lombok.Builder;

import java.util.Set;

@Builder
public record TeamDto(
        Long id,
        String name,
        String abbreviation,
        Set<Long> teamMembersIds
) {
}
