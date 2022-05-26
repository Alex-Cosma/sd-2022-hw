package com.raulp.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/sendNotification")
    @SendTo("/topic/notifications")
    public MessageDTO sendMessage(MessageDTO message) throws InterruptedException {
        Thread.sleep(100);
        return MessageDTO.builder().message(message.getMessage()).build();
    }

    public void sendNotification(MessageDTO message) {
        template.convertAndSend("/topic/notifications", message);
    }
}
