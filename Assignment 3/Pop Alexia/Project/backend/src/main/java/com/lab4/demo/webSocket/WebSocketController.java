package com.lab4.demo.webSocket;

import com.lab4.demo.webSocket.model.RequestMessage;
import com.lab4.demo.webSocket.model.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public ResponseMessage sendMessage(RequestMessage message) throws Exception {
        Thread.sleep(1000);
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getContent()));
    }
}
