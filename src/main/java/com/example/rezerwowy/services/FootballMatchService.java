package com.example.rezerwowy.services;

import com.example.rezerwowy.repositories.FootballMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FootballMatchService {

    private final FootballMatchRepository footballMatchRepository;
}
