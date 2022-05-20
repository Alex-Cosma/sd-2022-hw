package com.example.youtubeish.video.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MediumThumbnail {
    @JsonProperty("url")
    private String url;

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("height")
    private Integer height;

}
