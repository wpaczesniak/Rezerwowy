package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.SeatAlreadyExistsException;
import com.example.rezerwowy.exceptions.SeatNotFoundException;

import com.example.rezerwowy.models.Seat;
import com.example.rezerwowy.repositories.SeatRepository;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SeatService {
	
	private final SeatRepository seatRepository;


    @Transactional
    public Seat getSeatById(Long seatId) {
        Seat seat = seatRepository.findById(seatId).orElse(null);
        return seat;
    }

    @Transactional
    public Seat createSeat(Seat seat) {
        if (seat.getId() != null && seatRepository.existsById(seat.getId())) {
            throw new SeatAlreadyExistsException(seat.getId());
        }
        return seatRepository.save(seat);
    }

	@Transactional
    public void deleteSeatById(Long seatId) {
        if (!seatRepository.existsById(seatId)) {
            throw new SeatNotFoundException(seatId);
        }
        seatRepository.deleteById(seatId);
    }
}



