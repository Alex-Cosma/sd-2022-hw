package com.lab4.demo.websocket;

import com.lab4.demo.websocket.dto.Message;
import com.lab4.demo.websocket.dto.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import static com.lab4.demo.UrlMapping.MESSAGE;
import static com.lab4.demo.UrlMapping.TOPIC;

@Controller
public class MessageController {

    @MessageMapping(MESSAGE)
    @SendTo(TOPIC)
    public ResponseMessage getMessage(final Message message) throws InterruptedException {
        Thread.sleep(1000); // simulated delay
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }
}
