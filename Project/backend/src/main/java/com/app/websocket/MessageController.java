package com.app.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/movie/messages")
    public ResponseMessage greeting(Message message) throws Exception {
        Thread.sleep(1000);
        return new ResponseMessage("A new movie was added! Check out " + HtmlUtils.htmlEscape(message.getContent()) + "!");
    }
}
