package com.nizaring.hotel_booking_app;

import com.nizaring.hotel_booking_app.dtos.NotificationDTO;
import com.nizaring.hotel_booking_app.enums.NotificationType;
import com.nizaring.hotel_booking_app.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HotelBookingAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelBookingAppApplication.class, args);
    }


}
