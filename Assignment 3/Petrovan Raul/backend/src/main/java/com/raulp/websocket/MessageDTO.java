package com.raulp.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class MessageDTO {
    private String message;
    private List<String> userIds;
}
