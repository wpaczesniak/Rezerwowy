package com.example.rezerwowy.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.example.rezerwowy.dtos.PaymentDto;
import com.example.rezerwowy.dtos.SeatDto;
import com.example.rezerwowy.factories.PaymentFactory;
import com.example.rezerwowy.factories.SeatFactory;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.models.Seat;
import com.example.rezerwowy.repositories.PaymentRepository;
import com.example.rezerwowy.repositories.SeatRepository;

public class SeatIT {
        @Autowired
    private TestRestTemplate restTemplate;


    @Test
    @DirtiesContext
    void should_createSeat_when_seatDoesntExist() {
		// given
        Seat seat = SeatFactory.createSeatProperCase1();
        seat.setId(null);

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
