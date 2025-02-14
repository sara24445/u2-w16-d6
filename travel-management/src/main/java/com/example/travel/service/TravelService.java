package com.example.travel.service;

import com.example.travel.model.Travel;
import com.example.travel.model.Employee;
import com.example.travel.model.Booking;
import com.example.travel.model.TravelStatus;
import com.example.travel.repository.TravelRepository;
import com.example.travel.repository.EmployeeRepository;
import com.example.travel.repository.BookingRepository;
import com.example.travel.exception.ResourceNotFoundException;
import com.example.travel.exception.BookingConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TravelService {

    @Autowired
    private TravelRepository travelRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public Travel createTravel(Travel travel) {
        return travelRepository.save(travel);
    }

    public Travel getTravelById(UUID id) {
        return travelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Travel not found with id: " + id));
    }

    public Travel updateTravel(UUID id, Travel travelDetails) {
        Travel travel = travelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Travel not found with id: " + id));

        travel.setDestination(travelDetails.getDestination());
        travel.setDate(travelDetails.getDate());

        return travelRepository.save(travel);
    }

    public void deleteTravel(UUID id) {
        Travel travel = travelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Travel not found with id: " + id));
        travelRepository.delete(travel);
    }

    public Travel assignEmployeeToTravel(UUID travelId, UUID employeeId, String notes) {
        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new ResourceNotFoundException("Travel not found with id: " + travelId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        // Verifica se il dipendente ha già una prenotazione per quella data
        if (bookingRepository.existsByEmployeeIdAndRequestDate(employeeId, travel.getDate())) {
            throw new BookingConflictException("Employee already has a booking for this date");
        }

        Booking booking = new Booking();
        booking.setTravel(travel);
        booking.setEmployee(employee);
        booking.setRequestDate(LocalDate.now()); // Data di richiesta = oggi
        booking.setNotes(notes);
        bookingRepository.save(booking);

        return travel;
    }

    public Travel updateTravelStatus(UUID travelId, TravelStatus newStatus) {
        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new ResourceNotFoundException("Travel not found with id: " + travelId));

        travel.setStatus(newStatus);
        return travelRepository.save(travel);
    }
}