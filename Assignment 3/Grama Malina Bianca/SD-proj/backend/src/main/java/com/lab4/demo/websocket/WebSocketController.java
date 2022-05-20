package com.lab4.demo.websocket;

import com.lab4.demo.websocket.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.lab4.demo.UrlMapping.SEND_MESSAGE;

@RestController
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    @PostMapping(SEND_MESSAGE)
    public void sendMessage(@RequestBody final Message message) {
        webSocketService.notifyFrontend(message.getMessageContent());
    }
}
