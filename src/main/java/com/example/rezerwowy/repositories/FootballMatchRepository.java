package com.example.rezerwowy.repositories;

import com.example.rezerwowy.models.FootballMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {
}
