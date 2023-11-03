package com.example.rezerwowy.mappers;

import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationMapper {
    @Lazy
    private final ReservationService reservationService;

    public Reservation mapReservationIdToReservation(Long reservationId) {
        return reservationService.getReservationById(reservationId);
    }
}
