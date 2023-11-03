package com.example.rezerwowy.factories;

import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.models.Stadium;
import com.example.rezerwowy.models.Team;

import java.time.LocalDateTime;


public class FootballMatchFactory {

    private final static long defaultID = 1L;

    public static FootballMatch createProperFootballMatchCase1() {
        return FootballMatch.builder()
                .id(defaultID)
                .date(LocalDateTime.of(2023, 1, 1, 15, 0))
                .stadium(new Stadium())
                .hostTeam(new Team())
                .guestTeam(new Team())
                .build();
        // TODO
    }
}
