package com.websocket;

import com.websocket.dto.Message;
import com.websocket.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.unbescape.html.HtmlEscape;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
public class MessageController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    String destination = "/topic/newposts";

    ExecutorService executorService =
            Executors.newFixedThreadPool(1);
    Future<?> submittedTask;

    @MessageMapping("/start")
    public void startTask(){
        System.out.println("startingTask");
        if ( submittedTask != null ){
            simpMessagingTemplate.convertAndSend(destination,
                    "Task already started");
            return;
        }
        simpMessagingTemplate.convertAndSend(destination,
                "Started task");
        submittedTask = executorService.submit(() -> {
            while(true){
                simpMessagingTemplate.convertAndSend(destination,
                        LocalDateTime.now().toString()
                                +": doing some work");
                Thread.sleep(10000);
            }
        });
    }

    @MessageMapping("/stop")
    @SendTo("/topic/newposts")
    public String stopTask(){
        if ( submittedTask == null ){
            return "Task not running";
        }
        try {
            submittedTask.cancel(true);
        }catch (Exception ex){
            ex.printStackTrace();
            return "Error occurred while stopping task due to: "
                    + ex.getMessage();
        }
        return "Stopped task";
    }
}
