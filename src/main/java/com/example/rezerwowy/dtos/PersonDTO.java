package com.example.rezerwowy.dtos;

import com.example.rezerwowy.models.Role;
import com.example.rezerwowy.models.Team;
import lombok.Builder;

import java.util.List;

@Builder
public record PersonDTO(
        Long id,
        String name,
        String surname,
        Long teamId,
        List<Role> roles
) { }
