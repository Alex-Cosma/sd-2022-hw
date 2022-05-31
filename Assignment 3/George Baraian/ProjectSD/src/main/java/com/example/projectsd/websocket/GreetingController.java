package com.example.projectsd.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @MessageMapping("/message")
    @SendTo("/sports")
    public Greeting greet(Message reservationInformation) throws InterruptedException {
        Thread.sleep(1000);
        return new Greeting("A reservation for " + reservationInformation.getName() + " has been made");
    }

}
