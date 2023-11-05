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
    public Set<Seat> getSeatsByIds(Set<Long> seatIds) {
        Set<Seat> seats = new HashSet<>();

        for (Long seatId : seatIds) {
            Seat seat = seatRepository.findById(seatId).orElse(null);
            if (seat != null) {
                seats.add(seat);
            }
        }

        return seats;
    }

    @Transactional
    public Seat createSeat(Seat seat) {
        if (seat.getId() != null && seatRepository.existsById(seat.getId())) {
            throw new SeatAlreadyExistsException(seat.getId());
        }
        return seatRepository.save(seat);
    }

	@Transactional
    public void deleteSeatsByIds(Set<Long> seatIds) {
        for (Long seatId : seatIds) {
            if (!seatRepository.existsById(seatId)) {
                throw new SeatNotFoundException(seatId);
            }
            seatRepository.deleteById(seatId);
        }
    }
}



