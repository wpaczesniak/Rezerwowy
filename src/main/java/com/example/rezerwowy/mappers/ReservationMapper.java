package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.FootballMatchDto;
import com.example.rezerwowy.dtos.ReservationDto;
import com.example.rezerwowy.exceptions.PaymentNotFoundException;
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
	private final FootballMatchMapper footballMatchMapper;
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
				.seatsId(seatsId)
				.footballMatchId(matchId)
				.paymentId(paymentId)
				.build();
	}

	public Reservation mapReservationDtoToReservation(ReservationDto reservationDto) {
		// TODO
		FootballMatchDto footballMatchDto = footballMatchService.getFootballMatchById(reservationDto.footballMatchId());
		FootballMatch footballMatch = footballMatchMapper.mapFootballMatchDtoToFootballMatch(footballMatchDto);

		Set<Seat> seats = reservationDto.seatsId().stream()
				.map(seatService::getSeatById)
				.collect(Collectors.toSet());
		Payment payment = null;

		if (reservationDto.paymentId() != null) {
			try {
				payment = paymentService.getPaymentById(reservationDto.paymentId());
			} catch (PaymentNotFoundException ignored) { }
		}

		return Reservation.builder()
				.id(reservationDto.id())
				.footballMatch(footballMatch)
				.seats(seats)
				.payment(payment)
				.build();
	}
}
