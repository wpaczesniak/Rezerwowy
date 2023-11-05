package com.example.rezerwowy.services;

import org.springframework.stereotype.Service;

import com.example.rezerwowy.exceptions.StadiumAlreadyExistsException;
import com.example.rezerwowy.exceptions.StadiumNotFoundException;
import com.example.rezerwowy.models.Stadium;
import com.example.rezerwowy.repositories.StadiumRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StadiumService {
    private final StadiumRepository stadiumRepository;

    @Transactional
    public Stadium createStadium(@Valid Stadium stadium) {
        if (stadium.getId() != null && stadiumRepository.existsById(stadium.getId())) {
            throw new StadiumAlreadyExistsException(stadium.getId());
        }
        return stadiumRepository.save(stadium);
    }

    @Transactional
    public Stadium getStadiumById(Long stadiumId) {
        return stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new StadiumNotFoundException(stadiumId));
    }

    @Transactional
    public void deleteStadiumById(Long stadiumId) {
        if (!stadiumRepository.existsById(stadiumId)) {
            throw new StadiumNotFoundException(stadiumId);
        }
        stadiumRepository.deleteById(stadiumId);
    }
    
}
