package com.example.rezerwowy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "footballMatch")
public class FootballMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private Money pricePerSeat = Money.of(10, "PLN");

    @NotNull
    private LocalDateTime date;

    @NotNull
    @ManyToOne
    private Team hostTeam;

    @NotNull
    @ManyToOne
    private Team guestTeam;

    @NotNull
    @ManyToOne
    private Stadium stadium;
}
