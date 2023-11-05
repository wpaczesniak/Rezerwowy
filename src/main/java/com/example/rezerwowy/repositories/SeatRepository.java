package com.example.rezerwowy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rezerwowy.models.Seat;



public interface SeatRepository extends JpaRepository<Seat, Long>  {

 
    
}
