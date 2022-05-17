package com.lab4.demo.webSocket.model;

import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class RequestMessage {
    private String content;
}
