package com.example.rezerwowy.mappers;

import com.example.rezerwowy.services.FootballMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FootballMatchMapper {
	@Lazy
	private final FootballMatchService footballMatchService;


}
