package com.example.youtubeish.playlist.dto;

import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.video.dto.VideoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDTO {

    private Long id;
    private List<VideoDTO> videos;
    private UserDTO user;
}
