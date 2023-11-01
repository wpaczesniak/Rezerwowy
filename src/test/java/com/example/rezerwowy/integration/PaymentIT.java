package com.example.rezerwowy.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentIT {
    @Autowired
    TestRestTemplate restTemplate;

//    @Test
//    @DirtiesContext
//    void should_notCreateANewTeam_when_teamWithTheSameNameExists() {
//        // given
//        Team team = new Team(null, "FC Barcelona", "FCB");
//        Team team2 = new Team(null, "FC Barcelona", "FCB");
//
//        // when
//        ResponseEntity<String> createResponse = restTemplate.postForEntity("/teams", team, String.class);
//        ResponseEntity<String> createResponse2 = restTemplate.postForEntity("/teams", team2, String.class);
//
//        //then
//        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(createResponse2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
}
