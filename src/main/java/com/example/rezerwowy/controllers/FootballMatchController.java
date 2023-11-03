package com.example.rezerwowy.controllers;

import com.example.rezerwowy.dtos.FootballMatchDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matches")
public class FootballMatchController {

    @PostMapping
    public ResponseEntity<Void> createFootballMatch(@RequestBody @Valid FootballMatchDto footballMatchDto) {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FootballMatchDto> getFootballMatchById(@PathVariable long id) {
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFootballMatchById(@PathVariable long id) {
        return ResponseEntity.badRequest().build();
    }
}
