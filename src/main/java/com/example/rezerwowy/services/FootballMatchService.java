package com.example.rezerwowy.services;

import com.example.rezerwowy.models.FootballMatch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FootballMatchService {
	@Transactional
	public FootballMatch getFootballMatchById(Long footballMatchId) {
		return null;
		// TODO
	}
}
