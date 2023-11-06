package com.example.rezerwowy.mappers;

import java.util.Set;
import java.util.stream.Collectors;

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
        Set<Long> seatsIds = stadium.getSeats() != null
                ? stadium.getSeats().stream().map(Seat::getId).collect(Collectors.toSet())
                : null;
        Set<Long> matchId = stadium.getFootballMatches() != null
                ? stadium.getFootballMatches().stream().map(FootballMatch::getId).collect(Collectors.toSet())
                : null;
        return StadiumDto.builder()
                .id(stadium.getId())
                .name(stadium.getName())
                .capacity(stadium.getCapacity())
                .seatIds(seatsIds)
                .footballMatchIds(matchId)
                .build();
    }

    public Stadium mapStadiumDtoToStadium(StadiumDto stadiumDto) {
        Set<Seat> seats = null;
        if (stadiumDto.seatIds() != null) {
            try {
                seats = seatService.getSeatsByIds(stadiumDto.seatIds());
            } catch (ReservationNotFoundException ignored) { }
        }
        Set<FootballMatch> footballMatches = null;
        if (stadiumDto.footballMatchIds() != null) {
            try {
                footballMatches = footballMatchService.getFootballMatchById(stadiumDto.footballMatchIds());
            } catch (SeatNotFoundException ignored) { }
        }
        return Stadium.builder()
                .id(stadiumDto.id())
                .name(stadiumDto.name())
                .capacity(stadiumDto.capacity())
                .seats(seats)
                .footballMatches(footballMatches)
                .build();
    }
}
