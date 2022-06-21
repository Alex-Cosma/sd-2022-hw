package com.app.banking.service.newsletter.channels;

import com.app.banking.data.dto.email.EmailDto;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.repo.UserRepository;
import com.app.banking.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmailNotificationChannel implements NotificationChannel {

    private final EmailService emailService;
    private final UserRepository userRepository;

    @Override
    public void sendNotification(String title, String message) {
        emailService.sendEmail(new EmailDto(title, message), getAllEmails());
    }

    // Cam cibanesc da mi lene, pls nu te supara
    private List<String> getAllEmails() {
        return userRepository.findAll().stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

}
