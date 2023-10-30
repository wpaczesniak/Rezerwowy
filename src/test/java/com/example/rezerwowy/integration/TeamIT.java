package com.example.rezerwowy.integration;

import com.example.rezerwowy.models.Team;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamIT {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DirtiesContext
    void should_createANewTeam_when_createTeam() {
        // given
        Team team = new Team(null, "FC Barcelona", "FCB");

        // when
        ResponseEntity<String> createResponse = restTemplate.postForEntity("/teams", team, String.class);
        URI newTeamLocation = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate.getForEntity(newTeamLocation, String.class);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");
        String abbreviation = documentContext.read("$.abbreviation");

        // then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(id).isNotNull();
        assertThat(name).isEqualTo(team.getName());
        assertThat(abbreviation).isEqualTo(team.getAbbreviation());
    }

    @Test
    @DirtiesContext
    void should_notCreateANewTeam_when_teamWithTheSameNameExists() {
        // given
        Team team = new Team(null, "FC Barcelona", "FCB");
        Team team2 = new Team(null, "FC Barcelona", "FCB");

        // when
        ResponseEntity<String> createResponse = restTemplate.postForEntity("/teams", team, String.class);
        ResponseEntity<String> createResponse2 = restTemplate.postForEntity("/teams", team2, String.class);

        //then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResponse2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext
    void should_createANewTeamCorrectly_when_given2TeamsWithTheSameId() {
        // given
        Team team = new Team(5L, "FC Barcelona", "FCB");
        Team team2 = new Team(5L, "Korona Kielce", "KOR");

        // when
        ResponseEntity<String> createResponse = restTemplate.postForEntity("/teams", team, String.class);
        ResponseEntity<String> createResponse2 = restTemplate.postForEntity("/teams", team2, String.class);

        URI newTeamLocation = createResponse.getHeaders().getLocation();
        URI newTeamLocation2 = createResponse2.getHeaders().getLocation();

        ResponseEntity<String> getResponse = restTemplate.getForEntity(newTeamLocation, String.class);
        ResponseEntity<String> getResponse2 = restTemplate.getForEntity(newTeamLocation2, String.class);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        DocumentContext documentContext2 = JsonPath.parse(getResponse2.getBody());

        Number id = documentContext.read("$.id");
        Number id2 = documentContext2.read("$.id");

        //then
        assertThat(id).isEqualTo(5L);
        assertThat(id2).isEqualTo(1L);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResponse2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
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

}
