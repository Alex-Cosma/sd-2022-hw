package com.example.gymapplication.email;

import com.example.gymapplication.user.model.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendMail(User user){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Welcome to GymApp");
        message.setText("Hello, "+user.getUsername() + "!\n" +
                "We are glad to have you as a member of our gym application!\n");
        message.setTo(user.getEmail());
        javaMailSender.send(message);

    }
}
