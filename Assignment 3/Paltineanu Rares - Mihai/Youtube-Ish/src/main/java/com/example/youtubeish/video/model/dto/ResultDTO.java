package com.example.youtubeish.video.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ResultDTO {
    @JsonProperty("items")
    private List<VideoDTO> videoDTOList = new ArrayList<>();
}
