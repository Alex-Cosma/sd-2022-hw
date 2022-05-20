package com.example.youtubeish.video.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VideoAPIDTO {

    @JsonProperty("id")
    private VideoID id;

    @JsonProperty("snippet")
    private Snippet snippet;
}
