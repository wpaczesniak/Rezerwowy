package com.example.rezerwowy.integration;

import com.example.rezerwowy.dtos.SeatDto;
import com.example.rezerwowy.factories.SeatFactory;
import com.example.rezerwowy.factories.StadiumFactory;
import com.example.rezerwowy.models.Stadium;
import com.example.rezerwowy.repositories.SeatRepository;
import com.example.rezerwowy.repositories.StadiumRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class SeatIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private StadiumRepository stadiumRepository;


    @Test
    @DirtiesContext
    void should_createSeat_when_seatDoesntExist() {
		// given
        SeatDto seat = SeatFactory.createSeatProperCase1Dto();
        Stadium stadium = StadiumFactory.createStadiumCase1();
        stadium.setId(seat.stadiumId());
        stadiumRepository.save(stadium);

        // when
        ResponseEntity<SeatDto> createResponse = restTemplate
            .postForEntity("/seats", seat, SeatDto.class);

// then
Assertions.assertAll(
        () -> assertThat(createResponse.getStatusCode())
                .isEqualTo(HttpStatus.CREATED),
        () -> assertThat(seatRepository.existsById(createResponse.getBody().id()))
                .isTrue()
);
	}
        
}
