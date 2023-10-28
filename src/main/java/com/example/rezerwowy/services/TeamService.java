package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.TeamNotFoundException;
import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.repositories.TeamRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public Collection<Team> getTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }

    public Team addTeam(@Valid Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, @Valid Team team) {
        if (!Objects.equals(id, team.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not equal id and team.id");
        }
        if (teamRepository.existsById(id)) {
            return teamRepository.save(team);
        } else {
            throw new TeamNotFoundException(id);
        }
    }

    public void deleteTeam(Long id) {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
        } else {
            throw new TeamNotFoundException(id);
        }
    }
}
