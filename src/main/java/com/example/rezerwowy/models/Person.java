package com.example.rezerwowy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="person")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Person {
    private static final int MIN_FIELD_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_SURNAME_LENGTH = 50;

    private static final String NAME_LENGTH_VALIDATION_MESSAGE =
            "Name length should be between " + MIN_FIELD_LENGTH  + " and " + MAX_NAME_LENGTH + " characters";
    private static final String SURNAME_LENGTH_VALIDATION_MESSAGE =
            "Surname length should be between " + MIN_FIELD_LENGTH  + " and " + MAX_SURNAME_LENGTH + " characters";

    private static final String REGEX_FOR_NAMES =
            "([a-zA-Z\\-\\.\\']+\\s?)+";
    private static final String NAMES_CHARS_VALIDATION_MESSAGE = "That is not a proper name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="person_id")
    private Long id;

    @Column(name="name", length = MAX_NAME_LENGTH)
    @NotEmpty
    @Size(min = MIN_FIELD_LENGTH, max = MAX_NAME_LENGTH, message = NAME_LENGTH_VALIDATION_MESSAGE)
    @Pattern(regexp = REGEX_FOR_NAMES, message = NAMES_CHARS_VALIDATION_MESSAGE)
    private String name;

    @Column(name="surname", length = MAX_SURNAME_LENGTH)
    @NotEmpty
    @Size(min = MIN_FIELD_LENGTH, max = MAX_SURNAME_LENGTH, message = SURNAME_LENGTH_VALIDATION_MESSAGE)
    @Pattern(regexp = REGEX_FOR_NAMES, message = NAMES_CHARS_VALIDATION_MESSAGE)
    private String surname;

    @PrimaryKeyJoinColumn(name="team_id")
    @ManyToOne
    private Team team;

    @PrimaryKeyJoinColumn(name="role_id")
    @OneToMany
    private List<Role> roles = new LinkedList<>();
}
