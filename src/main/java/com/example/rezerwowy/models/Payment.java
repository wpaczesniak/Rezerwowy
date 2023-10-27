package com.example.rezerwowy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Embedded
    @NotNull
    private Buyer buyer;

    @Column(name = "date")
    @NotNull
    @DateTimeFormat
    @PastOrPresent(message = "Reservation should be made in the past")
    private LocalDate date;

    @PrimaryKeyJoinColumn(name = "reservation_id")
    @OneToOne
    @NotNull
    private Reservation reservation;
}
