package com.app.banking.service.newsletter.channels;

import com.app.banking.data.dto.email.ExternalSimpleEmailDto;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.repo.UserRepository;
import com.app.banking.service.ExternalNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExternalNotificationChannel implements NotificationChannel {

    private final ExternalNotificationService externalNotificationService;
    private final UserRepository userRepository;

    @Override
    public void sendNotification(String title, String message) {
        externalNotificationService.sendSimpleEmail(ExternalSimpleEmailDto.builder()
                .subject(title)
                .text(message)
                .recipients(getAllEmails())
                .build());
        externalNotificationService.postSimpleMessage(getSimplePostMessage(title, message));
    }

    private String getSimplePostMessage(String title, String message) {
        return String.format("*%s*\n%s", title, message);
    }

    // Cam cibanesc da mi lene, pls nu te supara
    private List<String> getAllEmails() {
        return userRepository.findAll().stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

}
