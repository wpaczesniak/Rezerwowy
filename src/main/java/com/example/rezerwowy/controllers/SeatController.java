package com.example.rezerwowy.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rezerwowy.dtos.SeatDto;
import com.example.rezerwowy.models.Seat;
import com.example.rezerwowy.services.SeatService;

import jakarta.validation.Valid;

import com.example.rezerwowy.mappers.SeatMapper;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("seats")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;    
    private final SeatMapper seatMapper;
    
    @PostMapping
    public ResponseEntity<SeatDto> createSeat(@RequestBody @Valid SeatDto seatDtoToCreate) {
        Seat seatToCreate = seatMapper.mapSeatDtoToSeat(seatDtoToCreate);
        Seat createdSeat = seatService.createSeat(seatToCreate);
        SeatDto createdSeatDto = seatMapper.mapSeatToSeatDto(createdSeat);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSeatDto);
    }



    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> getSeatById(@PathVariable("id") Long seatId) {
        Seat seat =  seatService.getSeatById(seatId);
        SeatDto seatDto = seatMapper.mapSeatToSeatDto(seat);
        
        return ResponseEntity.status(HttpStatus.OK).body(seatDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeatById(@PathVariable("id") Long seatId) {
            seatService.deleteSeatById(seatId);
        
        return ResponseEntity.ok().build();
    }

}
