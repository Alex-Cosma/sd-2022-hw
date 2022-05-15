package com.example.youtubeish.video.model.dto;

import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.dto.UserDetailsImpl;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UploadVideoDTO {
    @JsonProperty("video")
    private VideoDTO video;

    @JsonProperty("user")
    private UserDTO user;
}
