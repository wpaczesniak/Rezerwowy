package com.example.rezerwowy.mappers;

import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.services.FootballMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FootballMatchMapper {
	@Lazy
	private final FootballMatchService footballMatchService;


}
