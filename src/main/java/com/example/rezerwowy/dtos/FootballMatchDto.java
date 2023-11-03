package com.example.rezerwowy.dtos;

import lombok.Builder;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

@Builder
public record FootballMatchDto(
        Long id,

        Money ticketPrice,

        LocalDateTime date,

        Long hostTeamId,

        Long guestTeamId,

        Long stadiumId
) {
}
