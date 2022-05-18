package com.example.youtubeish.video.dto;

import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.video.dto.api.VideoAPIDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UploadVideoDTO {
    @JsonProperty("video")
    private VideoAPIDTO video;

    @JsonProperty("user")
    private UserDTO user;
}
