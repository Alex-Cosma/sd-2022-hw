package com.example.youtubeish.video.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VideoDTO {

    @JsonProperty("id")
    private VideoID id;

    @JsonProperty("snippet")
    private Snippet snippet;
}
