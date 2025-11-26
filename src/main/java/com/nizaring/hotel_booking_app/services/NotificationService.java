package com.nizaring.hotel_booking_app.services;

import com.nizaring.hotel_booking_app.dtos.NotificationDTO;

public interface NotificationService {
    void sendEmail(NotificationDTO notificationDTO);

    void sendSms();

    void sendWhatsapp();
}
