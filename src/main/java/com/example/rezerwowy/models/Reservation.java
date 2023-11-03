package com.example.rezerwowy.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Reservation {
    @Id
    private Long id;

    @OneToOne
    private Payment payment;

    @OneToMany
    Set<Seat> seats = new HashSet<>();

    @ManyToOne
    FootballMatch footballMatch;

    // TODO
}
