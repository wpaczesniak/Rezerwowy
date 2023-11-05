package com.example.rezerwowy.controllers;

import com.example.rezerwowy.dtos.FootballMatchDto;
import com.example.rezerwowy.services.FootballMatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matches")
public class FootballMatchController {

    private final FootballMatchService footballMatchService;

    @PostMapping
    public ResponseEntity<Void> createFootballMatch(@RequestBody @Valid FootballMatchDto footballMatchDto) {
        footballMatchService.createFootballMatch(footballMatchDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FootballMatchDto> getFootballMatchById(@PathVariable long id) {
        FootballMatchDto matchDto = footballMatchService.getFootballMatchById(id);
        return ResponseEntity.ok(matchDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFootballMatchById(@PathVariable long id) {
        footballMatchService.deleteFootballMatchById(id);
        return ResponseEntity.noContent().build();
    }
}
