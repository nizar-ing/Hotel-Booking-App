package com.nizaring.hotel_booking_app.repositories;

import com.nizaring.hotel_booking_app.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}