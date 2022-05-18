package com.example.youtubeish.video.dto;

import com.example.youtubeish.video.dto.api.VideoAPIDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ResultDTO {
    @JsonProperty("items")
    private List<VideoAPIDTO> videoDTOList = new ArrayList<>();
}
