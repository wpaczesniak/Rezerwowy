package com.example.rezerwowy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Data
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name", length = MAX_NAME_LENGTH, nullable = false, unique = true)
    @NotEmpty
    private String name;
}
