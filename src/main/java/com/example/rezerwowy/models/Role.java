package com.example.rezerwowy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Table(name = "role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private static final int MAX_NAME_LENGTH = 20;

    private static final String NAME_LENGTH_VALIDATION_MESSAGE =
            "Name length must be less than " + MAX_NAME_LENGTH + " characters";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name", length = MAX_NAME_LENGTH, nullable = false, unique = true)
    @NotEmpty
    @Size(max = MAX_NAME_LENGTH, message = NAME_LENGTH_VALIDATION_MESSAGE)
    private String name;
}
