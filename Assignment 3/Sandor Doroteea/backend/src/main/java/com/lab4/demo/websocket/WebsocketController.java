package com.lab4.demo.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import org.springframework.web.util.HtmlUtils;

import static com.lab4.demo.UrlMapping.TRACKS;

@Controller
public class WebsocketController {
    @MessageMapping("/hello")
   // @SendTo("/topic/spending")
    @SendTo("/topic/greetings")
    //hello is request from client, greetings is response to client
    public Greeting sendMessage(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "! Enjoy your favourite music!");
    }

}