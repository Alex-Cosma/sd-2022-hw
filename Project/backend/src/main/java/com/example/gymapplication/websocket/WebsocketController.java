package com.example.gymapplication.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import static com.example.gymapplication.UrlMapping.NOTIFICATIONS;
import static com.example.gymapplication.UrlMapping.NOTIFY;

@Controller
public class WebsocketController {
    @MessageMapping(NOTIFY)
    @SendTo(NOTIFICATIONS)
    public ResponseMessage notify(Message message) throws Exception {
        Thread.sleep(1000);
        return new ResponseMessage("New training: " + HtmlUtils.htmlEscape(message.getContents()) + "!");
    }
}
