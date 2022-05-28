package com.forum.websocket.model;

import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Message {
    private String contents;
}
