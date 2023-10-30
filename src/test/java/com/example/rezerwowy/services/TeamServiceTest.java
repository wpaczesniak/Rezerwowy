package com.example.rezerwowy.services;

import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        teamService = new TeamService(teamRepository);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_getAllTeams() {
        // given
        List<Team> teamList = Arrays.asList(
                new Team(1L, "FC Barcelona", "FCB"),
                new Team(2L, "Real Madrid", "RMA")
        );
        Mockito.when(teamRepository.findAll()).thenReturn(teamList);

        // when
        teamService.getTeams();

        // then
        Mockito.verify(teamRepository).findAll();
    }

    @Test
    void should_callAppropriateMethodInRepository_when_getTeamById() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();
        Long id = team.getId();
        Mockito.when(teamRepository.findById(id)).thenReturn(Optional.of(team));

        // when
        teamService.getTeamById(id);

        // then
        Mockito.verify(teamRepository).findById(id);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_addTeam() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();
        Mockito.when(teamRepository.save(team)).thenReturn(team);

        // when
        teamService.addTeam(team);

        // then
        Mockito.verify(teamRepository).save(team);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_updateTeam() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();
        Team updatedTeam = Team.builder()
                .id(100L)
                .name("Korona Kielce")
                .abbreviation("KOR")
                .build();
        Long id = team.getId();
        Mockito.when(teamRepository.existsById(id)).thenReturn(true);

        // when
        teamService.updateTeam(id, updatedTeam);

        // then
        Mockito.verify(teamRepository).save(updatedTeam);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_deleteTeam() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();
        Long id = team.getId();
        Mockito.when(teamRepository.existsById(id)).thenReturn(true);

        // when
        teamService.deleteTeam(id);

        // then
        Mockito.verify(teamRepository).deleteById(id);
    }

}
