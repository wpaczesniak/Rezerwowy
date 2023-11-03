package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.TeamAlreadyExistsException;
import com.example.rezerwowy.exceptions.TeamNotFoundException;
import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.repositories.TeamRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public Collection<Team> getTeams() {
        return teamRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }

    @Transactional
    public Team createTeam(@Valid Team team) {
        if (teamRepository.existsByName(team.getName())) {
            throw new TeamAlreadyExistsException(team.getName());
        }
        return teamRepository.save(team);
    }

    @Transactional
    public Team updateTeam(Long id, @Valid Team team) {
        if (teamRepository.existsById(id)) {
            return teamRepository.save(team);
        } else {
            throw new TeamNotFoundException(id);
        }
    }

    @Transactional
    public void deleteTeam(Long id) {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
        } else {
            throw new TeamNotFoundException(id);
        }
    }
}
