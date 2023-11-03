package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.TeamDto;
import com.example.rezerwowy.models.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamMapper {

    public TeamDto mapTeamToTeamDto(Team team) {
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .abbreviation(team.getAbbreviation())
                .build();
    }

    public Team mapTeamDtoToTeam(TeamDto teamDto) {
        return Team.builder()
                .name(teamDto.name())
                .abbreviation(teamDto.abbreviation())
                .build();
    }
}
