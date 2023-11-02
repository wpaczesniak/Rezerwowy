package com.example.rezerwowy.mappers;

import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamMapper {
    @Lazy
    private final TeamService teamService;

    public Team mapTeamIdToTeam(Long teamId) {
        return teamService.getTeamById(teamId);
    }
}
