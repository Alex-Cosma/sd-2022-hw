package com.example.demo.websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebsocketController {


    @CrossOrigin
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public WebsocketMessage sendMessage(WebsocketMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new WebsocketMessage("New movie added to the app: " + message.getMessage());
    }

}
