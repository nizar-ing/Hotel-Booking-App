package com.nizaring.hotel_booking_app.entities;

import com.nizaring.hotel_booking_app.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_number")
    private Integer roomNumber;

    @Column(name = "room_type")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "availability")
    private Boolean availability = false;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "image_url")
    private String imageUrl;

}