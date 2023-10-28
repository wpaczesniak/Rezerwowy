package com.example.rezerwowy.repositories;

import com.example.rezerwowy.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
