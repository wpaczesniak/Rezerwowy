package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.ReservationDto;
import com.example.rezerwowy.exceptions.PaymentNotFoundException;
import com.example.rezerwowy.exceptions.ReservationNotFoundException;
import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.models.Seat;
import com.example.rezerwowy.services.FootballMatchService;
import com.example.rezerwowy.services.PaymentService;
import com.example.rezerwowy.services.ReservationService;
import com.example.rezerwowy.services.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationMapper {
	@Lazy
	private final ReservationService reservationService;

	@Lazy
	private final FootballMatchService footballMatchService;
	@Lazy
	private final SeatService seatService;
	@Lazy
	private final PaymentService paymentService;

	public ReservationDto mapReservationToReservationDto(Reservation reservation) {
		Long matchId = reservation.getFootballMatch() != null
				? reservation.getFootballMatch().getId()
				: null;
		Set<Long> seatsId = reservation.getSeats() != null
				? reservation.getSeats().stream().map(Seat::getId).collect(Collectors.toSet())
				: null;
		Long paymentId = reservation.getPayment() != null
				? reservation.getPayment().getId()
				: null;
		return ReservationDto.builder()
				.id(reservation.getId())
				.publicId(reservation.getPublicId())
				.comment(reservation.getComment())
				.seatIds(seatsId)
				.footballMatchId(matchId)
				.paymentId(paymentId)
				.build();
	}

	public Reservation mapReservationDtoToReservation(ReservationDto reservationDto) {
		FootballMatch footballMatch = null;
		if (reservationDto.footballMatchId() != null) {
			try {
				footballMatch = footballMatchService.getFootballMatchById(reservationDto.footballMatchId());
			} catch (ReservationNotFoundException ignored) { }
		}

		Set<Seat> seats = null;
		if (reservationDto.seatIds() != null) {
			try {
				seats = seatService.getSeatsByIds(reservationDto.seatIds());
			} catch (ReservationNotFoundException ignored) { }
		}

		Payment payment = null;
		if (reservationDto.paymentId() != null) {
			try {
				payment = paymentService.getPaymentById(reservationDto.paymentId());
			} catch (PaymentNotFoundException ignored) { }
		}

		return Reservation.builder()
				.id(reservationDto.id())
				.comment(reservationDto.comment())
				.footballMatch(footballMatch)
				.seats(seats)
				.payment(payment)
				.build();
	}
}
