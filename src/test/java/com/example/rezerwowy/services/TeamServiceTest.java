package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.TeamAlreadyExistsException;
import com.example.rezerwowy.exceptions.TeamNotFoundException;
import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.repositories.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
                new Team(1L, "FC Barcelona", "FCB", null),
                new Team(2L, "Real Madrid", "RMA", null)
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
    void should_returnObjectWithCorrectData_when_getTeamByIdAndItExists() {
        //given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();

        Long id = team.getId();
        Mockito.when(teamRepository.findById(id)).thenReturn(Optional.of(team));

        //when
        Team foundTeam = teamService.getTeamById(id);

        //then
        Assertions.assertAll(
                () -> assertThat(foundTeam.getId()).isEqualTo(team.getId()),
                () -> assertThat(foundTeam.getName()).isEqualTo(team.getName()),
                () -> assertThat(foundTeam.getAbbreviation()).isEqualTo(team.getAbbreviation())
        );
    }

    @Test
    void should_throwException_when_getTeamByIdButItDoesntExist() {
        //given
        Long id = 100L;
        Mockito.when(teamRepository.findById(id)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> teamService.getTeamById(id)).isInstanceOf(TeamNotFoundException.class);
    }

    @Test
    void should_callAppropriateMethodInRepository_when_createTeam() {
        // given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();
        Mockito.when(teamRepository.save(team)).thenReturn(team);

        // when
        teamService.createTeam(team);

        // then
        Mockito.verify(teamRepository).save(team);
    }

    @Test
    void should_returnObjectWithTheSameDate_when_createTeam() {
        //given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();
        Mockito.when(teamRepository.save(team)).thenReturn(team);

        //when
        Team createdTeam = teamService.createTeam(team);

        //then
        Assertions.assertAll(
                () -> assertThat(createdTeam.getId()).isEqualTo(team.getId()),
                () -> assertThat(createdTeam.getName()).isEqualTo(team.getName()),
                () -> assertThat(createdTeam.getAbbreviation()).isEqualTo(team.getAbbreviation())
        );
    }

    @Test
    void should_throwException_when_teamWithTheSameNameAlreadyExists() {
        //given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();
        Mockito.when(teamRepository.existsByName(team.getName())).thenReturn(true);

        //when then
        assertThatThrownBy(() -> teamService.createTeam(team)).isInstanceOf(TeamAlreadyExistsException.class);
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
    void should_returnObjectWithCorrectData_when_updateTeamByIdAndItExists() {
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();
        Mockito.when(teamRepository.save(team)).thenReturn(team);
        Mockito.when(teamRepository.existsById(team.getId())).thenReturn(true);

        //when
        Team updatedTeam = teamService.updateTeam(team.getId(), team);

        //then
        Assertions.assertAll(
                () -> assertThat(updatedTeam.getId()).isEqualTo(team.getId()),
                () -> assertThat(updatedTeam.getName()).isEqualTo(team.getName()),
                () -> assertThat(updatedTeam.getAbbreviation()).isEqualTo(team.getAbbreviation())
        );
    }

    @Test
    void should_throwException_when_updateTeamByIdButItDoesntExist() {
        //given
        Team team = Team.builder()
                .id(100L)
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();
        Long id = team.getId();
        Mockito.when(teamRepository.existsById(id)).thenReturn(false);

        //when then
        assertThatThrownBy(() -> teamService.updateTeam(id, team)).isInstanceOf(TeamNotFoundException.class);
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

    @Test
    void should_throwException_when_deleteTeamByIdButItDoesntExist() {
        //given
        Long id = 100L;
        Mockito.when(teamRepository.existsById(id)).thenReturn(false);

        //when then
        assertThatThrownBy(() -> teamService.deleteTeam(id)).isInstanceOf(TeamNotFoundException.class);
    }
}
