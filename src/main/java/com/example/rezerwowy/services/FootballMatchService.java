package com.example.rezerwowy.services;

import com.example.rezerwowy.dtos.FootballMatchDto;
import com.example.rezerwowy.exceptions.FootballMatchAlreadyExistsException;
import com.example.rezerwowy.exceptions.FootballMatchNotFoundException;
import com.example.rezerwowy.mappers.FootballMatchMapper;
import com.example.rezerwowy.models.FootballMatch;
import com.example.rezerwowy.repositories.FootballMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FootballMatchService {

    private final FootballMatchRepository footballMatchRepository;
    private final FootballMatchMapper mapper;

    @Transactional(readOnly = true)
    public FootballMatchDto getFootballMatchById(long id) {
        Optional<FootballMatch> optionalFootballMatch = footballMatchRepository.findById(id);

        return optionalFootballMatch.map(mapper::mapFootballMatchToFootballMatchDto)
                .orElseThrow(() -> new FootballMatchNotFoundException(id));
    }

    @Transactional
    public void deleteFootballMatchById(long id) {
        if (!footballMatchRepository.existsById(id)) {
            throw new FootballMatchNotFoundException(id);
        }

        footballMatchRepository.deleteById(id);
    }

    @Transactional
    public FootballMatchDto createFootballMatch(FootballMatchDto footballMatchDto) {
        if (footballMatchRepository.existsByDateAndHostTeamIdAndGuestTeamId(
                footballMatchDto.date(),
                footballMatchDto.hostTeamId(),
                footballMatchDto.guestTeamId()
        )) {
            throw new FootballMatchAlreadyExistsException();
        }

        FootballMatch toSave = mapper.mapFootballMatchDtoToFootballMatch(footballMatchDto);
        toSave.setId(null);
        FootballMatch savedMatch = footballMatchRepository.save(toSave);
        return mapper.mapFootballMatchToFootballMatchDto(savedMatch);
    }
}
