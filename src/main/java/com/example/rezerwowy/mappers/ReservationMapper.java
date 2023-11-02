package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.ReservationDto;
import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.models.Seat;
import com.example.rezerwowy.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationMapper {
	@Lazy
	private final ReservationService reservationService;

	@Lazy
	private final FootballMatchMapper footballMatchMapper;
	@Lazy
	private final SeatMapper seatMapper;
	@Lazy
	private final PaymentMapper paymentMapper;

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
				.seatsId(seatsId)
				.footballMatchId(matchId)
				.paymentId(paymentId)
				.build();
	}

	public Reservation mapReservationDtoToReservation(ReservationDto reservationDto) {
		FootballMatch footballMatch = footballMatchMapper
				.mapFootballMatchtIdToFootballMatch(reservationDto.footballMatchId());
		Set<Seat> seats = seatMapper
				.mapSeatsIdToSeats(reservationDto.seatsId());
		Payment payment = paymentMapper
				.mapPaymentIdToPayment(reservationDto.paymentId());
		return Reservation.builder()
				.id(reservationDto.id())
				.footballMatch(footballMatch)
				.seats(seats)
				.payment(payment)
				.build();
	}

	public Reservation mapReservationIdToReservation(Long reservationId) {
		return reservationService.getReservationById(reservationId);
	}
}
