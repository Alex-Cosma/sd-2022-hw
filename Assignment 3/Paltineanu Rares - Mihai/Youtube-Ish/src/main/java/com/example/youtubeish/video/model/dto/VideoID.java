package com.example.youtubeish.video.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VideoID {
    @JsonProperty("videoId")
    private String videoId;
}
