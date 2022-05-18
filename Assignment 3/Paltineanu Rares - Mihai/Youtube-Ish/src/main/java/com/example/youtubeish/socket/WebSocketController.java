package com.example.youtubeish.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/report")
    @SendTo("/topic/admin")
    public String report() {
        return "Abc";
    }
}
