package com.example.youtubeish.video.model.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VideoAPIDTO {

    @JsonProperty("id")
    private VideoID id;

    @JsonProperty("snippet")
    private Snippet snippet;
}
