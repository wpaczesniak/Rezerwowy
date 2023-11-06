package com.example.rezerwowy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Reservation {

	private static final int MAX_COMMENT_LENGTH = 500;
	private static final String COMMENT_LENGTH_VALIDATION_MESSAGE = "Comment length should be less than " + MAX_COMMENT_LENGTH + " characters";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservation_id")
	private Long id;

	@Column(name = "reservation_public_id")
	@NotNull
	@Builder.Default
	private UUID publicId = UUID.randomUUID();

	@Column(name = "reservation_comment", nullable = false, length = MAX_COMMENT_LENGTH)
	@Size(max = MAX_COMMENT_LENGTH, message = COMMENT_LENGTH_VALIDATION_MESSAGE)
	@NotNull
	private String comment;

	@OneToOne(mappedBy = "reservation")
	private Payment payment;

	@JoinColumn(name = "seat_id")
	@OneToMany
	Set<Seat> seats = new HashSet<>();

	@JoinColumn(name = "footballMatch_id")
	@ManyToOne
	FootballMatch footballMatch;


}

