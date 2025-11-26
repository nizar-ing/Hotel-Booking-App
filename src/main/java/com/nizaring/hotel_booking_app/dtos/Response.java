package com.nizaring.hotel_booking_app.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nizaring.hotel_booking_app.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    //generic
    private int status;
    private String message;

    //for login
    private String token;
    private UserRole role;
    private Boolean isActive;
    private String expirationTime;

    //user data output
    private UserDTO user;
    private List<UserDTO> users;

    //Booking data output
    private BookingDTO booking;
    private List<BookingDTO> bookings;

    //Room data output
    private RoomDTO room;
    private List<RoomDTO> rooms;

    //Payment data output
    private PaymentDTO payment;
    private List<PaymentDTO> payments;

    //Notification data output
    private NotificationDTO notification;
    private List<NotificationDTO> notifications;

    private final LocalDateTime timestamp = LocalDateTime.now();

}

