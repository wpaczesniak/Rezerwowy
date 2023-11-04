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
    @Column(name = "match_id")
    private Long id;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private BigDecimal pricePerSeat = new BigDecimal("10.00");

    @NotNull
    @Column(name = "date")
    private LocalDateTime date;

    @NotNull
    @Column(name = "host_team_id")
    private Long hostTeamId;

    @NotNull
    @Column(name = "guest_team_id")
    private Long guestTeamId;

    @NotNull
    @Column(name = "stadium_id")
    private Long stadiumId;
}
