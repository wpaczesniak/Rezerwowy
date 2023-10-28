package com.example.rezerwowy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    private static final int MIN_NAME_LENGTH = 5;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_ABBREVIATION_LENGTH = 3;

    private static final String NAME_LENGTH_VALIDATION_MESSAGE =
            "Name length must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name", length = MAX_NAME_LENGTH, nullable = false, unique = true)
    @NotEmpty
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH, message = NAME_LENGTH_VALIDATION_MESSAGE)
    private String name;

    @Column(name = "team_abbreviation", length = MAX_ABBREVIATION_LENGTH, nullable = false)
    @NotEmpty
    private String abbreviation;
}
