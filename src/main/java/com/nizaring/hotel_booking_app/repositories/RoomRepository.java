package com.nizaring.hotel_booking_app.repositories;

import com.nizaring.hotel_booking_app.entities.Room;
import com.nizaring.hotel_booking_app.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
            SELECT R FROM Room R
            WHERE
                R.id NOT IN (
                    SELECT B.room.id
                    FROM Booking B
                    WHERE :checkInDate <= B.checkOutDate
                    AND :checkOutDate >= B.checkInDate
                    AND B.bookingStatus IN (com.nizaring.hotel_booking_app.enums.BookingStatus.BOOKED, com.nizaring.hotel_booking_app.enums.BookingStatus.CHECKED_IN)
                )
                AND (:roomType IS NULL OR R.roomType = :roomType)
            """)
    List<Room> findAvailableRooms(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("roomType") RoomType roomType
    );


    @Query("""
                SELECT R FROM Room R
                WHERE CAST(R.roomNumber AS string) LIKE %:searchParam%
                   OR LOWER(R.roomType) LIKE LOWER(:searchParam)
                   OR CAST(R.pricePerNight AS string) LIKE %:searchParam%
                   OR CAST(R.capacity AS string) LIKE %:searchParam%
                   OR LOWER(R.description) LIKE LOWER(CONCAT('%', :searchParam, '%'))
            """)
    List<Room> searchRooms(@Param("searchParam") String searchParam);

}