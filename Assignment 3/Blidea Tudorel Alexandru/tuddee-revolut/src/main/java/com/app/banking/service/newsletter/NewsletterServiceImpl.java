package com.app.banking.service.newsletter;

import com.app.banking.service.newsletter.channels.NotificationChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsletterServiceImpl implements NewsletterService {

    private final List<NotificationChannel> notificationChannels;

    @Override
    public void sendNewsletter(String title, String message) {
        for (NotificationChannel notificationChannel: notificationChannels) {
            notificationChannel.sendNotification(title, message);
        }
    }

}
