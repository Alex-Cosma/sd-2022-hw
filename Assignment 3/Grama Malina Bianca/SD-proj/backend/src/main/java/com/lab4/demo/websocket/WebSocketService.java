package com.lab4.demo.websocket;

import com.lab4.demo.websocket.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.lab4.demo.UrlMapping.TOPIC;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyFrontend(final String message) {
        ResponseMessage responseMessage = new ResponseMessage(message);

        messagingTemplate.convertAndSend(TOPIC, responseMessage);
    }
}
