package com.example.travel.repository;

import com.example.travel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    boolean existsByEmployeeIdAndRequestDate(UUID employeeId, LocalDate requestDate);
}