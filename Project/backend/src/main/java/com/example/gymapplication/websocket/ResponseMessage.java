package com.example.gymapplication.websocket;

import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ResponseMessage {
    private String contents;
}
