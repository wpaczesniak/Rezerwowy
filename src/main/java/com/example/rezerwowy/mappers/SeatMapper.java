package com.example.rezerwowy.mappers;

import com.example.rezerwowy.services.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SeatMapper {
	@Lazy
	private final SeatService seatService;

}
