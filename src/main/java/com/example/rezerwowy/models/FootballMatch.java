package com.example.rezerwowy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "footballMatch",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "hostTeamId", "guestTeamId"})}
)
public class FootballMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private BigDecimal pricePerSeat = new BigDecimal("10.00");

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Long hostTeamId;

    @NotNull
    private Long guestTeamId;

    @NotNull
    private Long stadiumId;
}
