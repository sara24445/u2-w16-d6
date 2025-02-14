package com.example.travel.controller;

import com.example.travel.model.Travel;
import com.example.travel.model.TravelStatus;
import com.example.travel.service.TravelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @PostMapping
    public ResponseEntity<Travel> createTravel(@Valid @RequestBody Travel travel) {
        Travel createdTravel = travelService.createTravel(travel);
        return new ResponseEntity<>(createdTravel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Travel> getTravelById(@PathVariable UUID id) {
        Travel travel = travelService.getTravelById(id);
        return new ResponseEntity<>(travel, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Travel> updateTravel(@PathVariable UUID id, @Valid @RequestBody Travel travelDetails) {
        Travel updatedTravel = travelService.updateTravel(id, travelDetails);
        return new ResponseEntity<>(updatedTravel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravel(@PathVariable UUID id) {
        travelService.deleteTravel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{travelId}/assign/{employeeId}")
    public ResponseEntity<Travel> assignEmployeeToTravel(
            @PathVariable UUID travelId,
            @PathVariable UUID employeeId,
            @RequestParam(required = false) String notes) { // 'notes' diventa un parametro della richiesta
        Travel updatedTravel = travelService.assignEmployeeToTravel(travelId, employeeId, notes);
        return new ResponseEntity<>(updatedTravel, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Travel> updateTravelStatus(
            @PathVariable UUID id,
            @RequestParam TravelStatus status) {
        Travel updatedTravel = travelService.updateTravelStatus(id, status);
        return new ResponseEntity<>(updatedTravel, HttpStatus.OK);
    }
}
