package com.nizaring.hotel_booking_app.services.impl;

import com.nizaring.hotel_booking_app.dtos.NotificationDTO;
import com.nizaring.hotel_booking_app.entities.Notification;
import com.nizaring.hotel_booking_app.enums.NotificationType;
import com.nizaring.hotel_booking_app.repositories.NotificationRepository;
import com.nizaring.hotel_booking_app.services.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender javaMailSender;

    private final NotificationRepository notificationRepository;

    @Override
    @Async
    public void sendEmail(NotificationDTO notificationDTO) {
        log.info("Sending email ...");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(notificationDTO.getRecipient());
        simpleMailMessage.setSubject(notificationDTO.getSubject());
        simpleMailMessage.setText(notificationDTO.getBody());

        javaMailSender.send(simpleMailMessage);

        //SAVE TO DATABASE
        var notificationToSave = new Notification();
        notificationToSave.setRecipient(notificationDTO.getRecipient());
        notificationToSave.setSubject(notificationDTO.getSubject());
        notificationToSave.setBody(notificationDTO.getBody());
        notificationToSave.setBookingReference(notificationDTO.getBookingReference());
        notificationToSave.setType(NotificationType.EMAIL);

        notificationRepository.save(notificationToSave);
    }

    @Override
    public void sendSms() {

    }

    @Override
    public void sendWhatsapp() {

    }
}
