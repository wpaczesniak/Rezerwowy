package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.PaymentDto;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.models.Reservation;
import com.example.rezerwowy.services.PaymentService;
import com.example.rezerwowy.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMapper {
    @Lazy private final ReservationMapper reservationMapper;
    public PaymentDto mapPaymentToPaymentDto(Payment payment) {
        Long reservationId = payment.getReservation() != null
                ? payment.getReservation().getId()
                : null;

        return PaymentDto.builder()
                .id(payment.getId())
                .publicId(payment.getPublicId())
                .buyer(payment.getBuyer())
                .date(payment.getDate())
                .reservationId(reservationId)
                .build();
    }

    public Payment mapPaymentDtoToPayment(PaymentDto paymentDto) {
        Reservation reservation = reservationMapper
                .mapReservationIdToReservation(paymentDto.reservationId());

        return Payment.builder()
                .id(paymentDto.id())
                .buyer(paymentDto.buyer())
                .date(paymentDto.date())
                .reservation(reservation)
                .build();
    }
}
