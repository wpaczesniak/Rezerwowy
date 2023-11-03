package com.example.rezerwowy.factories;

import com.example.rezerwowy.models.Payment;

import java.time.LocalDate;

public class PaymentFactory {

    private static Long startId = 101L;
    public static Payment createProperPaymentCase1() {
        return Payment.builder()
                .id(startId++)
                .buyer(BuyerFactory.createProperBuyerCase1())
                .date(LocalDate.of(2020, 4, 15))
                .build();
    }

    public static Payment createProperPaymentCase2() {
        return Payment.builder()
                .id(startId++)
                .buyer(BuyerFactory.createProperBuyerCase2())
                .date(LocalDate.of(2022, 11, 5))
                .build();
    }

    public static Payment createProperPaymentCase3() {
        return Payment.builder()
                .id(startId++)
                .buyer(BuyerFactory.createProperBuyerCase3())
                .date(LocalDate.of(2023, 8, 6))
                .build();
    }
}
