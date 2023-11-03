package com.example.rezerwowy.factories;

import com.example.rezerwowy.models.Buyer;

public class BuyerFactory {
    public static Buyer createProperBuyerCase1() {
        return Buyer.builder()
                .name("Jan")
                .surname("Nowak")
                .email("jan-nowak@gmail.com")
                .build();
    }

    public static Buyer createProperBuyerCase2() {
        return Buyer.builder()
                .name("Anna")
                .surname("Kowalska-Nowak")
                .email("anna321@outlook.com")
                .build();
    }

    public static Buyer createProperBuyerCase3() {
        return Buyer.builder()
                .name("Jorja")
                .surname("Harmon Garner")
                .email("caitie2best7stokes.9@mail.com")
                .build();
    }
}
