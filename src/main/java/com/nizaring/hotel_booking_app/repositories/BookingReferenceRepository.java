package com.nizaring.hotel_booking_app.repositories;

import com.nizaring.hotel_booking_app.entities.BookingReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingReferenceRepository extends JpaRepository<BookingReference, Long> {
    Optional<BookingReference> findByReferenceNo(String referenceNo);
}