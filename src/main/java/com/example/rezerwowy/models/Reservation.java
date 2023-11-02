package com.example.rezerwowy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservation_id")
	private Long id;

	@Column(name = "reservation_public_id")
	@NotNull
	@Builder.Default
	private UUID publicId = UUID.randomUUID();

	@OneToOne(mappedBy = "reservation")
	private Payment payment;

	@JoinColumn(name = "seat_id")
	@OneToMany
	Set<Seat> seats = new HashSet<>();

	@JoinColumn(name = "footballMatch_id")
	@ManyToOne
	FootballMatch footballMatch;


}

