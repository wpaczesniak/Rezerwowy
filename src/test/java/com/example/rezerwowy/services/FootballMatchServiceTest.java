package com.example.rezerwowy.services;

import com.example.rezerwowy.dtos.FootballMatchDto;
import com.example.rezerwowy.exceptions.FootballMatchNotFoundException;
import com.example.rezerwowy.factories.FootballMatchFactory;
import com.example.rezerwowy.mappers.FootballMatchMapper;
import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.repositories.FootballMatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FootballMatchServiceTest {

    @Mock
    private FootballMatchRepository footballMatchRepository;
    private FootballMatchService footballMatchService;

    @BeforeEach
    void setup() {
        FootballMatchMapper footballMatchMapper = new FootballMatchMapper();
        this.footballMatchService = new FootballMatchService(this.footballMatchRepository, footballMatchMapper);
    }

    @Test
    void should_throwFootballMatchNotFoundException_when_getFootballMatchByIdAndIdNotInTheRepository() {
        //given
        long idNotInTheDatabase = 100L;
        Mockito.when(footballMatchRepository.findById(idNotInTheDatabase)).thenReturn(Optional.empty());

        // when


        // then
        assertThrows(
                FootballMatchNotFoundException.class,
                () -> footballMatchService.getFootballMatchById(idNotInTheDatabase)
        );
    }

    @Test
    void should_returnFootballMatchDto_when_getFootballMatchByIdAndIdInTheRepository() {
        //given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        FootballMatchDto expectedDto = new FootballMatchMapper().mapFootballMatchToFootballMatchDto(match);

        long id = match.getId();
        Mockito.when(footballMatchRepository.findById(id)).thenReturn(Optional.of(match));

        // when
        FootballMatchDto actualDto = footballMatchService.getFootballMatchById(id);

        // then
        assertAll(
                () -> assertEquals(expectedDto.id(), actualDto.id()),
                () -> assertEquals(expectedDto.ticketPrice(), actualDto.ticketPrice()),
                () -> assertEquals(expectedDto.date(), actualDto.date()),
                () -> assertEquals(expectedDto.hostTeamId(), actualDto.hostTeamId()),
                () -> assertEquals(expectedDto.guestTeamId(), actualDto.guestTeamId()),
                () -> assertEquals(expectedDto.stadiumId(), actualDto.stadiumId())
        );
    }

    @Test
    void should_throwFootballMatchNotFoundException_when_deleteByIdAndIdNotInTheRepository() {
        // given
        long idNotInDatabase = 100;
        Mockito.when(footballMatchRepository.existsById(idNotInDatabase)).thenReturn(false);

        // when

        // then
        assertThrows(
                FootballMatchNotFoundException.class,
                () -> footballMatchService.deleteFootballMatchById(idNotInDatabase)
        );
    }

    @Test
    void should_deleteFootballMatch_when_deleteByIdAndIdInTheRepository() {
        // given
        long id = 100;
        Mockito.when(footballMatchRepository.existsById(id)).thenReturn(true);

        // when
        footballMatchService.deleteFootballMatchById(id);

        // then
        Mockito.verify(footballMatchRepository).deleteById(id);
    }

}