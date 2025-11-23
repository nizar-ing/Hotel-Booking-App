package com.nizaring.hotel_booking_app.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nizaring.hotel_booking_app.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
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

    // Manual setters
    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public void setBooking(BookingDTO booking) {
        this.booking = booking;
    }

    public void setBookings(List<BookingDTO> bookings) {
        this.bookings = bookings;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }

    public void setPayments(List<PaymentDTO> payments) {
        this.payments = payments;
    }

    public void setNotification(NotificationDTO notification) {
        this.notification = notification;
    }

    public void setNotifications(List<NotificationDTO> notifications) {
        this.notifications = notifications;
    }
}

