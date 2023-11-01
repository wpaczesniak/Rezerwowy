package com.example.rezerwowy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Buyer {
    private static final int MIN_FIELD_LENGTH = 2;

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_SURNAME_LENGTH = 50;
    private static final int MAX_EMAIL_LENGTH = 75;

    private static final String NAME_LENGTH_VALIDATION_MESSAGE =
            "Name length should be between " + MIN_FIELD_LENGTH  + " and " + MAX_NAME_LENGTH + " characters";
    private static final String SURNAME_LENGTH_VALIDATION_MESSAGE =
            "Surname length should be between " + MIN_FIELD_LENGTH  + " and " + MAX_SURNAME_LENGTH + " characters";
    private static final String EMAIL_LENGTH_VALIDATION_MESSAGE =
            "Email length should be between " + MIN_FIELD_LENGTH  + " and " + MAX_EMAIL_LENGTH + " characters";

    @Column(name = "buyer_name", nullable = false, length = MAX_NAME_LENGTH)
    @NotEmpty
    @Size(min = MIN_FIELD_LENGTH, max = MAX_NAME_LENGTH, message = NAME_LENGTH_VALIDATION_MESSAGE)
    private String name;

    @Column(name = "buyer_surname", nullable = false, length = MAX_SURNAME_LENGTH)
    @NotEmpty
    @Size(min = MIN_FIELD_LENGTH, max = MAX_NAME_LENGTH, message = SURNAME_LENGTH_VALIDATION_MESSAGE)
    private String surname;

    @Column(name = "buyer_email", nullable = false, length = MAX_EMAIL_LENGTH)
    @NotEmpty
    @Size(min = MIN_FIELD_LENGTH, max = MAX_NAME_LENGTH, message = EMAIL_LENGTH_VALIDATION_MESSAGE)
    @Email
    private String email;
}
