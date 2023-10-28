package com.example.rezerwowy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.javamoney.moneta.Money;

@Entity
@Getter

public class FootballMatch {
    @Id
    private Long id;

    private Money pricePerSeat = Money.of(10, "PLN");
}
