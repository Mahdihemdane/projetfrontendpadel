package com.padel.reservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String to, String firstName, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to Padel Reservation System!");
        message.setText("Hello " + firstName + ",\n\n" +
                "Your account has been successfully created.\n" +
                "Your password is: " + password + "\n\n" +
                "You can now log in at http://localhost:4201/login\n\n" +
                "Best regards,\n" +
                "The Padel Team");
        mailSender.send(message);
    }
}
