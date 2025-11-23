package com.nizaring.hotel_booking_app.repositories;

import com.nizaring.hotel_booking_app.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, Long> {
}