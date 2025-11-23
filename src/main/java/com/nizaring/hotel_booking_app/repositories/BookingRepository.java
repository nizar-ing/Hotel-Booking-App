package com.nizaring.hotel_booking_app.repositories;

import com.nizaring.hotel_booking_app.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId); // Fetch all bookings for a specific user


    Optional<Booking> findByBookingReference(String bookingReference);


    @Query("""
               SELECT CASE WHEN COUNT(B) = 0 THEN true ELSE false END
                FROM Booking B
                WHERE B.room.id = :roomId
                  AND :checkInDate <= B.checkOutDate
                  AND :checkOutDate >= B.checkInDate
                  AND B.bookingStatus IN (com.nizaring.hotel_booking_app.enums.BookingStatus.BOOKED, com.nizaring.hotel_booking_app.enums.BookingStatus.CHECKED_IN)
            """)
    boolean isRoomAvailable(@Param("roomId") Long roomId,
                            @Param("checkInDate") LocalDate checkInDate,
                            @Param("checkOutDate") LocalDate checkOutDate);
}