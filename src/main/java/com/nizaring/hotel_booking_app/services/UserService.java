package com.nizaring.hotel_booking_app.services;

import com.nizaring.hotel_booking_app.dtos.LoginRequest;
import com.nizaring.hotel_booking_app.dtos.RegistrationRequest;
import com.nizaring.hotel_booking_app.dtos.Response;
import com.nizaring.hotel_booking_app.dtos.UserDTO;
import com.nizaring.hotel_booking_app.entities.User;

public interface UserService {
    Response registerUser(RegistrationRequest registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    Response getOwnAccountDetails();
    User getCurrentLoggedInUser();
    Response updateOwnAccount(UserDTO userDTO);
    Response deleteOwnAccount();
    Response getMyBookingHistory();
}
