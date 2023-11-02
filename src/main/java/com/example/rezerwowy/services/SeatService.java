package com.example.rezerwowy.services;

import com.example.rezerwowy.models.Seat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeatService {
	@Transactional
	public Seat getSeatById(Long seatId) {
		return null;
		// TODO
	}
}
