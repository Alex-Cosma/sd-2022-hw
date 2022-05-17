package com.example.youtubeish.comment.model.dto;

import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.video.model.dto.VideoDTO;
import com.example.youtubeish.video.model.dto.api.VideoAPIDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class AddCommentDTO {
    @JsonProperty("contnet")
    private String content;

    @JsonProperty("user")
    private UserDTO user;

    @JsonProperty("video")
    private VideoDTO video;
}
