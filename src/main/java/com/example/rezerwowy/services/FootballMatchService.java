package com.example.rezerwowy.services;

import com.example.rezerwowy.dtos.FootballMatchDto;
import com.example.rezerwowy.exceptions.FootballMatchNotFoundException;
import com.example.rezerwowy.mappers.FootballMatchMapper;
import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.repositories.FootballMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FootballMatchService {

    private final FootballMatchRepository footballMatchRepository;
    private final FootballMatchMapper footballMatchMapper;

    public FootballMatchDto getFootballMatchById(long id) {
        Optional<FootballMatch> optionalFootballMatch = footballMatchRepository.findById(id);

        return optionalFootballMatch.map(footballMatchMapper::mapFootballMatchToFootballMatchDto)
                .orElseThrow(() -> new FootballMatchNotFoundException(id));
    }

    public void deleteFootballMatchById(long id) {
        if (!footballMatchRepository.existsById(id)) {
            throw new FootballMatchNotFoundException(id);
        }

        footballMatchRepository.deleteById(id);
    }

    public void createFootballMatch(FootballMatchDto footballMatchDto) {
        throw new UnsupportedOperationException();
    }
}
