package com.example.rezerwowy.controllers;

import com.example.rezerwowy.dtos.SeatDto;
import com.example.rezerwowy.models.Seat;
import com.example.rezerwowy.models.Stadium;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rezerwowy.dtos.StadiumDto;
import com.example.rezerwowy.mappers.StadiumMapper;
import com.example.rezerwowy.services.StadiumService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RestController
@RequestMapping("stadiums")
@RequiredArgsConstructor
public class StadiumController {
    private final StadiumService stadiumService;
    private final StadiumMapper stadiumMapper;

    @PostMapping
    public ResponseEntity<StadiumDto> createStadium(@RequestBody @Valid StadiumDto stadiumDtoToCreate) {
        Stadium stadiumToCreate = stadiumMapper.mapStadiumDtoToStadium(stadiumDtoToCreate);
        Stadium createdStadium = stadiumService.createStadium(stadiumToCreate);
        StadiumDto createdStadiumDto = stadiumMapper.mapStadiumToStadiumDto(createdStadium);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStadiumDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StadiumDto> getStadiumById(@PathVariable("id") Long stadiumId) {
        Stadium stadium =  stadiumService.getStadiumById( stadiumId);
        StadiumDto stadiumDto = stadiumMapper.mapStadiumToStadiumDto(stadium);
        
        return ResponseEntity.status(HttpStatus.OK).body(stadiumDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStadiumById(@PathVariable("id") Long stadiumId) {
            stadiumService.deleteStadiumById(stadiumId);
        
        return ResponseEntity.ok().build();
    }
}
