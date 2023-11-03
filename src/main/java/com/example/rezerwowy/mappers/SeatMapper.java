package com.example.rezerwowy.mappers;

import com.example.rezerwowy.services.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SeatMapper {
	@Lazy
	private final SeatService seatService;

}
