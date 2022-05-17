package com.example.youtubeish.playlist;

import com.example.youtubeish.playlist.model.Playlist;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.mapper.UserMapper;
import com.example.youtubeish.video.mapper.VideoMapper;
import com.example.youtubeish.video.model.dto.VideoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserMapper userMapper;
    private final VideoMapper videoMapper;
    public Playlist create(UserDTO userDTO, List<VideoDTO> videoDTO) {
        Playlist playlist = Playlist.builder()
                .user(userMapper.fromDto(userDTO))
                .videos(videoDTO.stream().map(videoMapper::fromDto).collect(Collectors.toList()))
                .build();
        return playlistRepository.save(playlist);
    }
}
