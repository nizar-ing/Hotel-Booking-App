package com.nizaring.hotel_booking_app.security;

import com.nizaring.hotel_booking_app.entities.User;
import com.nizaring.hotel_booking_app.exceptions.NotFoundException;
import com.nizaring.hotel_booking_app.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User Email not found"));

        return new AuthUser(user);
    }
}
