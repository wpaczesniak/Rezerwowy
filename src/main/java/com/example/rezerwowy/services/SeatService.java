package com.example.rezerwowy.services;

import com.example.rezerwowy.models.Seat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class SeatService {
	@Transactional
	public Set<Seat> getSeatsByIds(Set<Long> seatId) {
		return null;
		// TODO
	}
}
