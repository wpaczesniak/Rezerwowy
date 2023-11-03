package com.example.rezerwowy.integration;

import com.example.rezerwowy.dtos.TeamDto;
import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.repositories.TeamRepository;
import org.junit.jupiter.api.Assertions;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class TeamIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @DirtiesContext
    void should_createTeam_when_teamDoesntExist() {
        // given
        Team team = new Team(null, "FC Barcelona", "FCB");

        // when
        ResponseEntity<TeamDto> createResponse = restTemplate.postForEntity("/teams", team, TeamDto.class);

        // then
        Assertions.assertAll(
                () -> assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(teamRepository.existsById(createResponse.getBody().id())).isTrue(),
                () -> assertThat(createResponse.getBody().id()).isNotNull(),
                () -> assertThat(createResponse.getBody().name()).isEqualTo(team.getName()),
                () -> assertThat(createResponse.getBody().abbreviation()).isEqualTo(team.getAbbreviation())
        );
    }

    @Test
    @DirtiesContext
    void should_returnBadRequest_when_teamWithTheSameNameExists() {
        // given
        Team team = new Team(null, "FC Barcelona", "FCB");
        Team team2 = new Team(null, "FC Barcelona", "FCB");

        // when
        ResponseEntity<TeamDto> createResponse = restTemplate.postForEntity("/teams", team, TeamDto.class);
        ResponseEntity<TeamDto> createResponse2 = restTemplate.postForEntity("/teams", team2, TeamDto.class);

        //then
        Assertions.assertAll(
                () -> assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(createResponse2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
        );

    }

    @Test
    @DirtiesContext
    void should_createTeamCorrectly_when_given2TeamsWithTheSameId() {
        // given
        Team team = new Team(5L, "FC Barcelona", "FCB");
        Team team2 = new Team(5L, "Korona Kielce", "KOR");

        // when
        ResponseEntity<TeamDto> createResponse = restTemplate.postForEntity("/teams", team, TeamDto.class);
        ResponseEntity<TeamDto> createResponse2 = restTemplate.postForEntity("/teams", team2, TeamDto.class);

        //then
        Assertions.assertAll(
                () -> assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(createResponse2.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(createResponse.getBody().id()).isNotNull(),
                () -> assertThat(createResponse2.getBody().id()).isNotNull(),
                () -> assertThat(createResponse.getBody().id()).isNotEqualTo(createResponse2.getBody().id())
        );
    }

    @Test
    @DirtiesContext
    void should_notCreateANewTeam_when_nameIsNull() {
        // given
        Team team = new Team(null, null, "FCB");

        // when
        ResponseEntity<String> createResponse = restTemplate.postForEntity("/teams", team, String.class);

        //then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext
    void should_notCreateANewTeam_when_abbreviationIsNull() {
        // given
        Team team = new Team(null, "FC Barcelona", null);

        // when
        ResponseEntity<String> createResponse = restTemplate.postForEntity("/teams", team, String.class);

        //then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext
    void should_returnCorrectData_when_getExistingTeam() {
        //given
        Team team = new Team(null, "FC Barcelona", "FCB");
        Team savedTeam = teamRepository.save(team);

        //when
        ResponseEntity<TeamDto> getResponse = restTemplate.getForEntity("/teams/" + team.getId(), TeamDto.class);

        //then
        Assertions.assertAll(
                () -> assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(getResponse.getBody().id()).isNotNull(),
                () -> assertThat(getResponse.getBody().name()).isEqualTo(savedTeam.getName()),
                () -> assertThat(getResponse.getBody().abbreviation()).isEqualTo(savedTeam.getAbbreviation())
        );
    }

    @Test
    @DirtiesContext
    void should_returnNotFound_when_getTeamWithIdThatIsNotPresentInDatabase() {
        // given
        Long id = 100L;

        // when
        ResponseEntity<TeamDto> getResponse = restTemplate.getForEntity("/teams/" + id, TeamDto.class);

        // then
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    @DirtiesContext
    void should_returnNotFound_when_updatedTeamDoesntExist() {
        // given
        Team team = new Team(null, "FC Barcelona", "FCB");
        HttpEntity<Team> request = new HttpEntity<>(team);
        Long id = 100L;

        // when
        ResponseEntity<TeamDto> updateResponse = restTemplate.exchange("/teams/" + id, HttpMethod.PUT, request, TeamDto.class);

        //then
        Assertions.assertAll(
                () -> assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
        );

    }

    @Test
    @DirtiesContext
    void should_updateTeam_when_teamExists() {
        // given
        Team team = new Team(null, "FC Barcelona", "FCB");
        Team savedTeam = teamRepository.save(team);
        Long id = savedTeam.getId();
        Team updatedTeam = new Team(null, "Korona Kielce", "KOR");
        HttpEntity<Team> request = new HttpEntity<>(updatedTeam);


        // when
        ResponseEntity<TeamDto> updateResponse = restTemplate.exchange("/teams/" + id, HttpMethod.PUT, request, TeamDto.class);

        //then
        Assertions.assertAll(
                () -> assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(updateResponse.getBody().name()).isEqualTo(updatedTeam.getName()),
                () -> assertThat(updateResponse.getBody().abbreviation()).isEqualTo(updatedTeam.getAbbreviation())
        );

    }

    @Test
    void should_returnNotFound_when_deletedTeamDoesntExist() {
        // given
        Long id = 100L;

        // when
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/teams/" + id, HttpMethod.DELETE, null, Void.class);

        //then
        Assertions.assertAll(
                () -> assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
        );

    }

    @Test
    @DirtiesContext
    void should_deleteInRepository_when_deleteExistingTeam() {
        //given
        Team team = new Team(null, "FC Barcelona", "FCB");
        Team savedTeam = teamRepository.save(team);

        //when
        restTemplate.delete("/teams/" + savedTeam.getId());

        //then
        assertThat(teamRepository.existsById(savedTeam.getId())).isFalse();
    }


}
