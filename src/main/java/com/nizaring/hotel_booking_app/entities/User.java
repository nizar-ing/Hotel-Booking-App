package com.nizaring.hotel_booking_app.entities;

import com.nizaring.hotel_booking_app.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "email")
    private String email;


    @Column(name = "password")
    private String password;


    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;


    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;


    @Column(name = "is_active")
    private Boolean isActive;


    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}