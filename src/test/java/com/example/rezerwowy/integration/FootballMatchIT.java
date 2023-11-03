package com.example.rezerwowy.integration;

import com.example.rezerwowy.dtos.FootballMatchDto;
import com.example.rezerwowy.factories.FootballMatchFactory;
import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.repositories.FootballMatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class FootballMatchIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Mock
    private FootballMatchRepository footballMatchRepository;

    @Test
    void should_returnStatusCodeNotFound_when_getFootballMatchByIdAndIdNotInTheDatabase() {
        // given
        long idNotInTheDatabase = 100;

        // when
        ResponseEntity<FootballMatchDto> getEntity =
                testRestTemplate.getForEntity("/matches/{id}", FootballMatchDto.class, idNotInTheDatabase);


        // then
        assertEquals(HttpStatus.NOT_FOUND, getEntity.getStatusCode());
    }

    @Test
    void should_returnNoContent_when_getFootballMatchByIdAndIdNotInTheDatabase() {
        // given
        long idNotInTheDatabase = 100;

        // when
        ResponseEntity<FootballMatchDto> getEntity =
                testRestTemplate.getForEntity("/matches/{id}", FootballMatchDto.class, idNotInTheDatabase);

        // then
        assertNull(getEntity.getBody());
    }

    @Test
    void should_returnStatusCodeOk_when_getFootballMatchByIdAndIdInTheDatabase() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        Mockito.when(footballMatchRepository.findById(match.getId())).thenReturn(Optional.of(match));

        // when
        ResponseEntity<FootballMatchDto> getEntity =
                testRestTemplate.getForEntity("/matches/{id}", FootballMatchDto.class, match.getId());

        // then
        assertEquals(HttpStatus.OK, getEntity.getStatusCode());
    }

}
