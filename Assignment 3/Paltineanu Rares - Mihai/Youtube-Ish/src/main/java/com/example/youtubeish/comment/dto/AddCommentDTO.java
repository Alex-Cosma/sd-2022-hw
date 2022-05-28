package com.example.youtubeish.comment.dto;

import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.video.dto.VideoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentDTO {
    @JsonProperty("content")
    private String content;

    @JsonProperty("user")
    private UserDTO user;

    @JsonProperty("video")
    private VideoDTO video;
}
