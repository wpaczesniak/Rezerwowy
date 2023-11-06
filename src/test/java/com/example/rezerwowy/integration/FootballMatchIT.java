package com.example.rezerwowy.integration;

import com.example.rezerwowy.dtos.FootballMatchDto;
import com.example.rezerwowy.factories.FootballMatchFactory;
import com.example.rezerwowy.mappers.FootballMatchMapper;
import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.repositories.FootballMatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class FootballMatchIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
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
    void should_returnNoBody_when_getFootballMatchByIdAndIdNotInTheDatabase() {
        // given
        long idNotInTheDatabase = 100;

        // when
        ResponseEntity<FootballMatchDto> getEntity =
                testRestTemplate.getForEntity("/matches/{id}", FootballMatchDto.class, idNotInTheDatabase);

        // then
        assertNull(getEntity.getBody());
    }

    @Test
    @DirtiesContext
    void should_returnStatusCodeOk_when_getFootballMatchByIdAndIdInTheDatabase() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        footballMatchRepository.save(match);

        // when
        ResponseEntity<FootballMatchDto> getEntity =
                testRestTemplate.getForEntity("/matches/{id}", FootballMatchDto.class, match.getId());

        // then
        assertEquals(HttpStatus.OK, getEntity.getStatusCode());
    }

    @Test
    @DirtiesContext
    void should_returnCorrectBody_when_getFootballMatchByIdAndIdInTheDatabase() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        footballMatchRepository.save(match);

        // when
        ResponseEntity<FootballMatchDto> getEntity =
                testRestTemplate.getForEntity("/matches/{id}", FootballMatchDto.class, match.getId());
        FootballMatchDto body = getEntity.getBody();

        // then
        assertAll(
                () -> assertEquals(match.getId(), body.id()),
                () -> assertEquals(match.getDate(), body.date()),
                () -> assertEquals(match.getPricePerSeat(), body.ticketPrice()),
                () -> assertEquals(match.getHostTeamId(), body.hostTeamId()),
                () -> assertEquals(match.getGuestTeamId(), body.guestTeamId()),
                () -> assertEquals(match.getStadiumId(), body.stadiumId())
        );
    }

    @Test
    void should_returnNotFoundStatus_when_deleteFootballMatchByIdAndIdNotInTheDatabase() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();

        // when
        ResponseEntity<Void> deleteEntity = testRestTemplate
                .exchange("/matches/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class, match.getId());

        // then
        assertEquals(HttpStatus.NOT_FOUND, deleteEntity.getStatusCode());
    }

    @Test
    @DirtiesContext
    void should_returnStatusNoContent_when_deleteFootballMatchByIdAndIdInTheDatabase() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        footballMatchRepository.save(match);
        assertTrue(footballMatchRepository.existsById(match.getId()));

        // when
        ResponseEntity<Void> deleteEntity = testRestTemplate
                .exchange("/matches/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class, match.getId());

        // then
        assertEquals(HttpStatus.NO_CONTENT, deleteEntity.getStatusCode());
    }

    @Test
    @DirtiesContext
    void should_deleteFootballMatch_when_deleteFootballMatchByIdAndIdInTheDatabase() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        footballMatchRepository.save(match);
        assertTrue(footballMatchRepository.existsById(match.getId()));

        // when
        ResponseEntity<Void> deleteEntity = testRestTemplate
                .exchange("/matches/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class, match.getId());

        // then
        assertFalse(footballMatchRepository.existsById(match.getId()));
    }

    @Test
    @DirtiesContext
    void should_returnStatusBadRequest_when_createFootballMatchAndMatchAlreadyExists() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        FootballMatchDto matchDto = new FootballMatchMapper().mapFootballMatchToFootballMatchDto(match);
        footballMatchRepository.save(match);
        assertTrue(footballMatchRepository.existsById(match.getId()));

        // when
        ResponseEntity<Void> postForEntity = testRestTemplate
                .postForEntity("/matches", matchDto, Void.class);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, postForEntity.getStatusCode());
    }

    @Test
    @DirtiesContext
    void should_returnStatusBadRequest_when_createFootballMatchAndMatchDateNull() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        match.setDate(null);
        FootballMatchDto matchDto = new FootballMatchMapper().mapFootballMatchToFootballMatchDto(match);

        // when
        ResponseEntity<Void> postForEntity = testRestTemplate
                .postForEntity("/matches", matchDto, Void.class);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, postForEntity.getStatusCode());
    }

    @Test
    @DirtiesContext
    void should_returnStatusBadRequest_when_createFootballMatchAndMatchStadiumNull() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        match.setStadiumId(null);
        FootballMatchDto matchDto = new FootballMatchMapper().mapFootballMatchToFootballMatchDto(match);

        // when
        ResponseEntity<Void> postForEntity = testRestTemplate
                .postForEntity("/matches", matchDto, Void.class);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, postForEntity.getStatusCode());
    }

    @Test
    @DirtiesContext
    void should_returnStatusBadRequest_when_createFootballMatchAndMatchHostTeamNull() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        match.setHostTeamId(null);
        FootballMatchDto matchDto = new FootballMatchMapper().mapFootballMatchToFootballMatchDto(match);

        // when
        ResponseEntity<Void> postForEntity = testRestTemplate
                .postForEntity("/matches", matchDto, Void.class);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, postForEntity.getStatusCode());
    }

    @Test
    @DirtiesContext
    void should_returnStatusBadRequest_when_createFootballMatchAndMatchGuestTeamNull() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        match.setGuestTeamId(null);
        FootballMatchDto matchDto = new FootballMatchMapper().mapFootballMatchToFootballMatchDto(match);

        // when
        ResponseEntity<Void> postForEntity = testRestTemplate
                .postForEntity("/matches", matchDto, Void.class);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, postForEntity.getStatusCode());
    }

    @Test
    @DirtiesContext
    void should_returnStatusCreated_when_createdFootballMatchSuccessfully() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        FootballMatchDto matchDto = new FootballMatchMapper().mapFootballMatchToFootballMatchDto(match);
        assertFalse(footballMatchRepository.existsById(match.getId()));

        // when
        ResponseEntity<Void> postForEntity = testRestTemplate
                .postForEntity("/matches", matchDto, Void.class);

        // then
        assertEquals(HttpStatus.CREATED, postForEntity.getStatusCode());
    }

    @Test
    @DirtiesContext
    void should_returnSaveMatchInRepository_when_createdFootballMatchSuccessfully() {
        // given
        FootballMatch match = FootballMatchFactory.createProperFootballMatchCase1();
        FootballMatchDto matchDto = new FootballMatchMapper().mapFootballMatchToFootballMatchDto(match);
        assertFalse(footballMatchRepository.existsById(match.getId()));

        // when
        ResponseEntity<Void> postForEntity = testRestTemplate
                .postForEntity("/matches", matchDto, Void.class);

        // then
        assertTrue(footballMatchRepository.existsById(match.getId()));
    }
}
