package com.example.rezerwowy.repositories;

import com.example.rezerwowy.models.FootballMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {

    boolean existsByDateAndHostTeamIdAndGuestTeamId(LocalDateTime date, Long hostTeamId, Long guestTeamId);

    Optional<FootballMatch> getFootballMatchByDateAndHostTeamIdAndGuestTeamId(LocalDateTime date, Long hostTeamId, Long guestTeamId);
}
