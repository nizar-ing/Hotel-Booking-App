package com.nizaring.hotel_booking_app.services;

import com.nizaring.hotel_booking_app.dtos.BookingDTO;
import com.nizaring.hotel_booking_app.dtos.Response;

public interface BookingService {
    Response getAllBookings();
    Response createBooking(BookingDTO bookingDTO);
    Response findBookingByReferenceNo(String  bookingReference);
    Response updateBooking(BookingDTO bookingDTO);
}
