package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.SeatDto;
import com.example.rezerwowy.exceptions.SeatNotFoundException;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.models.Seat;
import com.example.rezerwowy.models.Stadium;
import com.example.rezerwowy.services.ReservationService;
import com.example.rezerwowy.services.SeatService;
import com.example.rezerwowy.services.StadiumService;

import lombok.RequiredArgsConstructor;

import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SeatMapper {
	@Lazy
	private final SeatService seatService;

	@Lazy
	private final StadiumService stadiumService;

	@Lazy
	private final ReservationService reservationService;

	public SeatDto mapSeatToSeatDto(Set<Seat> seats) {
		Long stadiumId = null;
		Long reservationId = null;
		if (!seats.isEmpty()) {
			Seat seat = seats.iterator().next();
			stadiumId = seat.getStadium() != null ? seat.getStadium().getId() : null;
			reservationId = seat.getReservation() != null ? seat.getReservation().getId() : null;
		}
		return SeatDto.builder()
				.id(seats.iterator().next().getId())
				.seatNumber(seats.iterator().next().getSeatNumber())
				.stadiumId(stadiumId)
				.reservationId(reservationId)
				.build();
	}



	public Seat mapSeatDtoToSeat(SeatDto seatDto) {
		Stadium stadium = null;
		if (seatDto.stadiumId() != null) {
			try {
				stadium = stadiumService.getStadiumById(seatDto.stadiumId());
			} catch (SeatNotFoundException ignored) { }
		}
		Reservation reservation = null;
		if (seatDto.reservationId() != null) {
			try {
				reservation = reservationService.getReservationById(seatDto.reservationId());
			} catch (SeatNotFoundException ignored) { }
		}

		return Seat.builder()
				.id(seatDto.id())
				.seatNumber(seatDto.seatNumber())
				.stadium(stadium)
				.build();
	}

    public SeatDto mapSeatToSeatDto(Seat createdSeat) {
        return null;
    }
}
