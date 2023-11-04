package com.example.rezerwowy.controllers;

import com.example.rezerwowy.dtos.TeamDto;
import com.example.rezerwowy.mappers.TeamMapper;
import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.services.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @GetMapping
    public ResponseEntity<Collection<TeamDto>> getTeams() {
        Collection<Team> teams = teamService.getTeams();
        Collection<TeamDto> teamDtos = teams.stream()
                .map(teamMapper::mapTeamToTeamDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(teamDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable("id") Long id) {
        Team team = teamService.getTeamById(id);
        TeamDto teamDto = teamMapper.mapTeamToTeamDto(team);

        return ResponseEntity.status(HttpStatus.OK).body(teamDto);
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@RequestBody @Valid TeamDto teamDto) {
        Team teamToCreate = teamMapper.mapTeamDtoToTeam(teamDto);
        Team createdTeam = teamService.createTeam(teamToCreate);
        TeamDto createdTeamDto = teamMapper.mapTeamToTeamDto(createdTeam);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeamDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable("id") Long id, @RequestBody @Valid TeamDto teamDto) {
        Team teamToUpdate = teamMapper.mapTeamDtoToTeam(teamDto);
        Team updatedTeam = teamService.updateTeam(id, teamToUpdate);
        TeamDto updatedTeamDto = teamMapper.mapTeamToTeamDto(updatedTeam);

        return ResponseEntity.status(HttpStatus.OK).body(updatedTeamDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") Long id) {
        teamService.deleteTeam(id);

        return ResponseEntity.noContent().build();
    }

}
