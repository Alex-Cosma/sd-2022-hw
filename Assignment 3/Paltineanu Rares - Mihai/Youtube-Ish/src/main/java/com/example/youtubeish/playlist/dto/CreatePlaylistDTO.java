package com.example.youtubeish.playlist.dto;

import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.video.dto.VideoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreatePlaylistDTO {

    private UserDTO user;

    private List<VideoDTO> videos;
}
