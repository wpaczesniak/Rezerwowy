package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.FootballMatchDto;
import com.example.rezerwowy.models.FootballMatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FootballMatchMapper {

    public FootballMatchDto mapFootballMatchToFootballMatchDto(FootballMatch footballMatch) {
        return FootballMatchDto.builder()
                .id(footballMatch.getId())
                .ticketPrice(footballMatch.getPricePerSeat())
                .date(footballMatch.getDate())
                .hostTeamId(footballMatch.getHostTeamId())
                .guestTeamId(footballMatch.getGuestTeamId())
                .stadiumId(footballMatch.getStadiumId())
                .build();
    }

    public FootballMatch mapFootballMatchDtoToFootballMatch(FootballMatchDto footballMatchDto) {
        return FootballMatch.builder()
                .id(footballMatchDto.id())
                .pricePerSeat(footballMatchDto.ticketPrice())
                .date(footballMatchDto.date())
                .hostTeamId(footballMatchDto.hostTeamId())
                .guestTeamId(footballMatchDto.guestTeamId())
                .stadiumId(footballMatchDto.stadiumId())
                .build();
    }
}
