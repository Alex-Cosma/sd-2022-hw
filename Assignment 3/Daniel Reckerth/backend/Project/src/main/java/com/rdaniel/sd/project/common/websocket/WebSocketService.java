package com.rdaniel.sd.project.common.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

  private final SimpMessagingTemplate simpleMessageTemplate;

  public void sendMessage(String id, String payloadMessage) {
    simpleMessageTemplate.convertAndSend("/topic/" + id, payloadMessage);
  }
}
