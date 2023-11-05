package com.example.rezerwowy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "stadium")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class Stadium {

    private static final int CAPACITY = 50000;
    private static final String CAPACITY_VALIDATION_MESSAGE = "Capacity should be less than " + CAPACITY;
    @Id
    @OneToMany
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
    @Size(max = CAPACITY, message = CAPACITY_VALIDATION_MESSAGE)
    @NotNull
    private int capacity;

}
