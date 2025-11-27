package com.nizaring.hotel_booking_app.services.impl;

import com.nizaring.hotel_booking_app.dtos.*;
import com.nizaring.hotel_booking_app.entities.Booking;
import com.nizaring.hotel_booking_app.entities.Room;
import com.nizaring.hotel_booking_app.entities.User;
import com.nizaring.hotel_booking_app.enums.BookingStatus;
import com.nizaring.hotel_booking_app.enums.PaymentStatus;
import com.nizaring.hotel_booking_app.exceptions.InvalidBookingStateAndDateException;
import com.nizaring.hotel_booking_app.exceptions.NotFoundException;
import com.nizaring.hotel_booking_app.repositories.BookingRepository;
import com.nizaring.hotel_booking_app.repositories.RoomRepository;
import com.nizaring.hotel_booking_app.services.BookingCodeGenerator;
import com.nizaring.hotel_booking_app.services.BookingService;
import com.nizaring.hotel_booking_app.services.NotificationService;
import com.nizaring.hotel_booking_app.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final BookingCodeGenerator bookingCodeGenerator;

    @Override
    public Response getAllBookings() {
        List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<BookingDTO> bookingDTOList = modelMapper.map(bookingList, new TypeToken<List<BookingDTO>>() {
        }.getType());

        /*for(BookingDTO bookingDTO: bookingDTOList){
            bookingDTO.setUser(null);
            bookingDTO.setRoom(null);
        }*/

        /*bookingDTOList.forEach(bookingDTO -> {
            bookingDTO.setUser(null);
            bookingDTO.setRoom(null);
        });*/

        // Best approach to return bookings with related users and rooms
        bookingDTOList.forEach(bookingDTO -> {
            if (bookingDTO.getUser() != null) {
                Long userId = bookingDTO.getUser().getId();
                bookingDTO.setUser(new UserDTO());
                bookingDTO.getUser().setId(userId);
            }
            if (bookingDTO.getRoom() != null) {
                Long roomId = bookingDTO.getRoom().getId();
                bookingDTO.setRoom(new RoomDTO());
                bookingDTO.getRoom().setId(roomId);
            }
        });

        var response = new Response();
        response.setStatus(200);
        response.setMessage("Success");
        response.setBookings(bookingDTOList);

        return response;
    }

    @Override
    public Response createBooking(BookingDTO bookingDTO) {
        User currentUser = userService.getCurrentLoggedInUser();

        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new NotFoundException("Room Not Found"));


        //validation: Ensure the check-in date is not before today
        if (bookingDTO.getCheckInDate().isBefore(LocalDate.now())) {
            throw new InvalidBookingStateAndDateException("check in date cannot be before today ");
        }

        //validation: Ensure the check-out date is not before check in date
        if (bookingDTO.getCheckInDate().isBefore(bookingDTO.getCheckInDate())) {
            throw new InvalidBookingStateAndDateException("check out date cannot be before check in date ");
        }

        //validation: Ensure the check-in date is not same as check out date
        if (bookingDTO.getCheckInDate().isEqual(bookingDTO.getCheckOutDate())) {
            throw new InvalidBookingStateAndDateException("check in date cannot be equal to check out date ");
        }

        //validate room availability
        boolean isAvailable = bookingRepository.isRoomAvailable(room.getId(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());
        if (!isAvailable) {
            throw new InvalidBookingStateAndDateException("Room is not available for the selected date ranges");
        }

        //calculate the total price needed to pay for the stay
        BigDecimal totalPrice = calculateTotalPrice(room, bookingDTO);
        String bookingReference = bookingCodeGenerator.generateBookingReference();

        //create and save the booking
        Booking booking = new Booking();
        booking.setUser(currentUser);
        booking.setRoom(room);
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        booking.setTotalPrice(totalPrice);
        booking.setBookingReference(bookingReference);
        booking.setBookingStatus(BookingStatus.BOOKED);
        booking.setPaymentStatus(PaymentStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());

        bookingRepository.save(booking); //save to database

        //generate the payment url which will be sent via mail
        String paymentUrl = "http://localhost:3000/payment/" + bookingReference + "/" + totalPrice;

        log.info("PAYMENT LINK: {}", paymentUrl);

        //send notification via email
        var notificationDTO = new NotificationDTO();
        notificationDTO.setBookingReference(bookingReference);
        notificationDTO.setRecipient(currentUser.getEmail());
        notificationDTO.setSubject("Booking Confirmation");
        notificationDTO.setBody(String.format("Your booking has been created successfully. Please proceed with your payment using the payment link below " +
                "\n%s", paymentUrl));

        notificationService.sendEmail(notificationDTO);// sending email

        var response = new Response();
        response.setStatus(200);
        response.setMessage("Booking is successfully created");
        response.setBooking(bookingDTO);

        return response;
    }

    @Override
    public Response findBookingByReferenceNo(String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(()-> new NotFoundException("Booking with reference No: " + bookingReference + "Not found"));

        BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);

        var response = new Response();
        response.setStatus(200);
        response.setMessage("Success");
        response.setBooking(bookingDTO);

        return response;
    }

    @Override
    public Response updateBooking(BookingDTO bookingDTO) {
        if (bookingDTO.getId() == null) throw new NotFoundException("Booking id is required");

        Booking existingBooking = bookingRepository.findById(bookingDTO.getId())
                .orElseThrow(()-> new NotFoundException("Booking Not Found"));

        if (bookingDTO.getBookingStatus() != null) {
            existingBooking.setBookingStatus(bookingDTO.getBookingStatus());
        }

        if (bookingDTO.getPaymentStatus() != null) {
            existingBooking.setPaymentStatus(bookingDTO.getPaymentStatus());
        }

        bookingRepository.save(existingBooking);

        var response = new Response();
        response.setStatus(200);
        response.setMessage("Booking Successfully Updated");

        return response;
    }


    private BigDecimal calculateTotalPrice(Room room, BookingDTO bookingDTO){
        BigDecimal pricePerNight = room.getPricePerNight();
        long days = ChronoUnit.DAYS.between(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());
        return pricePerNight.multiply(BigDecimal.valueOf(days));
    }
}
