package com.example.rezerwowy.mappers;

import java.util.Set;

import org.hibernate.annotations.Comment;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.stereotype.Service;

import com.example.rezerwowy.dtos.SeatDto;
import com.example.rezerwowy.dtos.StadiumDto;
import com.example.rezerwowy.exceptions.ReservationNotFoundException;
import com.example.rezerwowy.exceptions.SeatNotFoundException;
import com.example.rezerwowy.exceptions.StadiumNotFoundException;
import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.models.Seat;
import com.example.rezerwowy.models.Stadium;
import com.example.rezerwowy.services.ReservationService;
import com.example.rezerwowy.services.SeatService;
import com.example.rezerwowy.services.StadiumService;
import com.example.rezerwowy.services.FootballMatchService;
import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.models.Stadium;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StadiumMapper {
    @Lazy
    private final StadiumService stadiumService;

    @Lazy
    private final SeatService seatService;

    @Lazy
    private final FootballMatchService footballMatchService;

    public StadiumDto mapStadiumToStadiumDto(Stadium stadium) {
        Set<Long> seatsId = stadium.getSeats() != null
                ? stadium.getSeats().getId()
                : null;
        Long reservation = stadium.getReservation() != null
                ? stadium.getReservation().getId()
                : null;
        Long matchId = stadium.getFootballMatch() != null
                ? stadium.getFootballMatch().getId()
                : null;
        return StadiumDto.builder()
                .id(stadium.getId())
                .stadiumName(stadium.getStadiumName())
                .capacity(stadium.getCapacity())
                .seatId(seatsId)
                .footballMatchId(matchId)
                .build();
    }

    public Stadium mapStadiumDtoToStadium(StadiumDto stadiumDto) {
        Seat seat = null;
        if (stadiumDto.seatId() != null) {
            try {
                seat = seatService.getSeatById(stadiumDto.seatId());
            } catch (SeatNotFoundException ignored) { }
        }
        Reservation reservation = null;
        if (stadiumDto.reservationId() != null) {
            try {
                reservation = reservationService.getReservationById(stadiumDto.reservationId());
            } catch (ReservationNotFoundException ignored) { }
        }
        FootballMatch footballMatch = null;
        if (stadiumDto.footballMatchId() != null) {
            try {
                footballMatch = footballMatchService.getFootballMatchById(stadiumDto.footballMatchId());
            } catch (SeatNotFoundException ignored) { }
        }
        return Stadium.builder()
                .id(stadiumDto.id())
                .stadiumName(stadiumDto.stadiumName())
                .capacity(stadiumDto.capacity())
                .seat(seat)
                .footballMatch(footballMatch)
                .build();
    }
}
