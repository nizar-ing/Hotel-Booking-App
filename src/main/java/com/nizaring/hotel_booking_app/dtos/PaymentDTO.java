package com.nizaring.hotel_booking_app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nizaring.hotel_booking_app.enums.PaymentGateway;
import com.nizaring.hotel_booking_app.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDTO {
    private Long id;
    private BookingDTO booking;

    private String transactionId;

    private BigDecimal amount;

    private PaymentGateway paymentMethod; //e,g PayPal. Stripe, flutterwave, paystack

    private LocalDateTime paymentDate;

    private PaymentStatus status; //failed, e.t.c

    private String bookingReference;
    private String failureReason;

    private String approvalLink; //paypal payment approval URL
}
