package com.example.youtubeish.video.model.dto;

import com.example.youtubeish.user.dto.UserDetailsImpl;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadVideoDTO {
    @JsonProperty("video")
    private VideoDTO video;

    @JsonProperty("user")
    private UserDetailsImpl user;
}
