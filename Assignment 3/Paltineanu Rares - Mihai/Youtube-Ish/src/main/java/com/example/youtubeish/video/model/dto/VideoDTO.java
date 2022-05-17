package com.example.youtubeish.video.model.dto;

import com.example.youtubeish.comment.model.dto.CommentDTO;
import com.example.youtubeish.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class VideoDTO {
    private Long id;

    private String title;

    private String thumbnailUrl;

    private String description;

    private String channelTitle;

    private String videoId;

    private List<CommentDTO> comments;

    private UserDTO user;
}
