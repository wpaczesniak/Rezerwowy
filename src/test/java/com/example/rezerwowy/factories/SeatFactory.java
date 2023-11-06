package com.example.rezerwowy.factories;

import com.example.rezerwowy.dtos.SeatDto;
import com.example.rezerwowy.models.Seat;

public class SeatFactory {
    private static Long id = 101L;
//    public static Seat createSeatProperCase1() {
//        return Seat.builder()
//            .id(id++)
//            .seatNumber(1L)
//            .stadium(1L)
//            .build();
//    }

    public static SeatDto createSeatProperCase1Dto() {
        return SeatDto.builder()
                .id(id++)
                .seatNumber(1L)
                .stadiumId(1L)
                .build();
    }

//    private static Seat createSeatProperCase2() {
//        return Seat.builder()
//            .id(id++)
//            .seatNumber(2L)
//            .stadium(1L)
//            .build();
//    }

    private static SeatDto createSeatProperCase2Dto() {
        return SeatDto.builder()
                .id(id++)
                .seatNumber(2L)
                .stadiumId(1L)
                .build();
    }

//    private static Seat createSeatProperCase3() {
//        return Seat.builder()
//            .id(id++)
//            .seatNumber(3L)
//            .stadium(1L)
//            .build();
//    }

    private static SeatDto createSeatProperCase3Dto() {
        return SeatDto.builder()
                .id(id++)
                .seatNumber(3L)
                .stadiumId(1L)
                .build();
    }
}
