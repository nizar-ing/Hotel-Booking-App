package com.nizaring.hotel_booking_app.services.impl;

import com.nizaring.hotel_booking_app.dtos.*;
import com.nizaring.hotel_booking_app.entities.Booking;
import com.nizaring.hotel_booking_app.entities.User;
import com.nizaring.hotel_booking_app.enums.UserRole;
import com.nizaring.hotel_booking_app.exceptions.InvalidCredentialException;
import com.nizaring.hotel_booking_app.exceptions.NotFoundException;
import com.nizaring.hotel_booking_app.repositories.BookingRepository;
import com.nizaring.hotel_booking_app.repositories.UserRepository;
import com.nizaring.hotel_booking_app.security.JwtUtils;
import com.nizaring.hotel_booking_app.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;

    @Override
    public Response registerUser(RegistrationRequest registrationRequest) {
        UserRole role = UserRole.CUSTOMER;

        if (registrationRequest.getRole() != null) {
            role = registrationRequest.getRole();
        }
        var userToSave = new User();
        userToSave.setFirstName(registrationRequest.getFirstName());
        userToSave.setLastName(registrationRequest.getLastName());
        userToSave.setEmail(registrationRequest.getEmail());
        userToSave.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userToSave.setRole(role);
        userToSave.setPhoneNumber(registrationRequest.getPhoneNumber());
        userToSave.setIsActive(Boolean.TRUE);

        userRepository.save(userToSave);

        var response = new Response();
        response.setStatus(200);
        response.setMessage("User registered successfully");

        return response;
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new NotFoundException("Email Not Found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Password doesn't match");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        var response = new Response();
        response.setStatus(200);
        response.setToken(token);
        response.setMessage("User logged in successfully");
        response.setRole(user.getRole());
        response.setIsActive(user.getIsActive());
        response.setExpirationTime("2 months");

        return response;
    }

    @Override
    public Response getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<UserDTO> userDTOList = modelMapper.map(users, new TypeToken<List<UserDTO>>(){}.getType());

        var response = new Response();
        response.setStatus(200);
        response.setUsers(userDTOList);
        response.setMessage("Success");

        return response;
    }

    @Override
    public Response getOwnAccountDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User Not Found"));


        log.info("Inside getOwnAccountDetails user email is {}", email);

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        var response = new Response();
        response.setStatus(200);
        response.setUser(userDTO);
        response.setMessage("Success");

        return response;
    }

    @Override
    public User getCurrentLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User Not Found"));
    }

    @Override
    public Response updateOwnAccount(UserDTO userDTO) {
        User existingUser = getCurrentLoggedInUser();
        log.info("Inside update user");

        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getFirstName() != null) existingUser.setFirstName(userDTO.getFirstName());
        if (userDTO.getLastName() != null) existingUser.setLastName(userDTO.getLastName());
        if (userDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(userDTO.getPhoneNumber());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(existingUser);

        var response = new Response();
        response.setStatus(200);
        response.setMessage("User updated successfully");
        return response;
    }

    @Override
    public Response deleteOwnAccount() {
        User user = getCurrentLoggedInUser();
        userRepository.delete(user);

        var response = new Response();
        response.setStatus(200);
        response.setMessage("User deleted successfully");
        return response;
    }

    @Override
    public Response getMyBookingHistory() {
        User user = getCurrentLoggedInUser();

        List<Booking> bookingList = bookingRepository.findByUserId(user.getId());
        List<BookingDTO> bookingDTOList = modelMapper.map(bookingList, new TypeToken<List<BookingDTO>>(){}.getType());

        var response = new Response();
        response.setStatus(200);
        response.setBookings(bookingDTOList);
        response.setMessage("Success");

        return response;
    }
}
