package com.example.travel.repository;

import com.example.travel.model.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TravelRepository extends JpaRepository<Travel, UUID> {
}
