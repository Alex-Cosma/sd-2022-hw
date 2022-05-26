package com.forum.websocket;

import com.forum.websocket.model.Message;
import com.forum.websocket.model.Response;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {
    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public Response notify(Message request) {
//        return new Response("New Post in " + HtmlUtils.htmlEscape(request.getContents()) + "!");
        return new Response("New Post in!");
    }
}
