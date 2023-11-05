package com.example.rezerwowy.factories;

import com.example.rezerwowy.dtos.SeatDto;
import com.example.rezerwowy.models.Seat;

public class SeatFactory {
    private static Long id = 101L;
    public static Seat createSeatProperCase1() {
        return Seat.builder()
            .id(id++)
            .seatNumber(1L)
            .stadiumId(1L)
            .build();
    }

    private static Seat createSeatProperCase2() {
        return Seat.builder()
            .id(id++)
            .seatNumber(2L)
            .stadiumId(1L)
            .build();
    }

    private static Seat createSeatProperCase3() {
        return Seat.builder()
            .id(id++)
            .seatNumber(3L)
            .stadiumId(1L)
            .build();
    }
}
