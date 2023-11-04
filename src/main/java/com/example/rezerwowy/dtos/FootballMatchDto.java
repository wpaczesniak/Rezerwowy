package com.example.rezerwowy.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record FootballMatchDto(
        Long id,

        BigDecimal ticketPrice,

        LocalDateTime date,

        Long hostTeamId,

        Long guestTeamId,

        Long stadiumId
) {
}
