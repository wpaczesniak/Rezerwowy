package com.example.rezerwowy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "stadium")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadium_id")
    private Long id;

    @Column(name = "stadium_name")  
    @NotNull
    private String name;

    @Column(name = "city_stadium")
    @NotNull
    private String city;

    @Column(name = "capacity")
    @NotNull
    private Integer capacity;


    @OneToMany
    private Set<FootballMatch> footballMatches;

    @JoinColumn(name = "seat_id")
    @OneToMany
    Set<Seat> seats;

}
