package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.FootballMatchDto;
import com.example.rezerwowy.models.FootballMatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FootballMatchMapper {

    // private final TeamService teamService;

    public FootballMatchDto mapFootballMatchToFootballMatchDto(FootballMatch footballMatch) {
        return FootballMatchDto.builder()
                .id(footballMatch.getId())
                .ticketPrice(footballMatch.getPricePerSeat())
                .date(footballMatch.getDate())
                .hostTeamId(footballMatch.getHostTeam().getId())
                .guestTeamId(footballMatch.getGuestTeam().getId())
                .stadiumId(footballMatch.getStadium().getId())
                .build();
    }

    public FootballMatch mapFootballMatchDtoToFootballMatch(FootballMatchDto footballMatchDto) {
//        Team hostTeam = teamService.findById(footballMatchDto.hostTeamId());
//        Team guestTeam = teamService.findById(footballMatchDto.guestTeamId());
//
//        return FootballMatch.builder()
//                .id(footballMatchDto.id())
//                .date(footballMatchDto.date())
//                .hostTeam()
//                .guestTeam()
//                .stadium()
//                .build()
        return null;
    }
}
