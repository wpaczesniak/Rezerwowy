package com.example.rezerwowy.services;

import com.example.rezerwowy.models.FootballMatch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class FootballMatchService {
	@Transactional
	public Set<FootballMatch> getFootballMatchById(Set<Long> footballMatchId) {
		return null;
		// TODO
	}
	public FootballMatch getFootballMatchById(Long footballMatchId) {
		return null;
		// TODO
	}
}
