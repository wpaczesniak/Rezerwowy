package com.example.rezerwowy.mappers;

import com.example.rezerwowy.models.Seat;
import com.example.rezerwowy.services.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SeatMapper {
	@Lazy
	private final SeatService seatService;

	public Set<Seat> mapSeatsIdToSeats(Set<Long> seatsId) {
		return seatsId.stream()
				.map(seatService::getSeatById)
				.filter(seat -> seat != null)
				.collect(Collectors.toSet());
	}
}
