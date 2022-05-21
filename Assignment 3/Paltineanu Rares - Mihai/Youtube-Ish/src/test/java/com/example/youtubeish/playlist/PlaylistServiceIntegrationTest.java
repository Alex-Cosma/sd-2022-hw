package com.example.youtubeish.playlist;

import com.example.youtubeish.playlist.dto.PlaylistDTO;
import com.example.youtubeish.playlist.model.Playlist;
import com.example.youtubeish.user.UserRepository;
import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.VideoRepository;
import com.example.youtubeish.video.model.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.example.youtubeish.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlaylistServiceIntegrationTest {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Test
    void create() {
        User user = newUser();
        user = userRepository.save(user);
        Video video = newVideo();
        video.setUser(user);
        Video video2 = newVideo();
        video2.setUser(user);

        video = videoRepository.save(video);
        video2 = videoRepository.save(video2);

        PlaylistDTO playlistDTO = playlistService.create(toDto(user), List.of(
                videoToDTO(video),
                videoToDTO(video2)
        ));
        assertNotNull(playlistDTO);
        assertEquals(user.getEmail(), playlistDTO.getUser().getEmail());
    }

    @Test
    void getUserPlaylists() {
        User user = newUser();
        user = userRepository.save(user);
        Video video = newVideo();
        video.setUser(user);
        Video video2 = newVideo();
        video2.setUser(user);

        video = videoRepository.save(video);
        video2 = videoRepository.save(video2);

        playlistService.create(toDto(user), List.of(
                videoToDTO(video),
                videoToDTO(video2)
        ));

        playlistService.create(toDto(user), List.of(
                videoToDTO(video2)
        ));

        List<PlaylistDTO> playlistDTOS = playlistService.getUserPlaylists(user.getId());
        assertEquals(2, playlistDTOS.size());
    }

    @Test
    void deletePlaylistById() {
        User user = newUser();
        user = userRepository.save(user);
        Video video = newVideo();
        video.setUser(user);
        Video video2 = newVideo();
        video2.setUser(user);

        video = videoRepository.save(video);
        video2 = videoRepository.save(video2);

        PlaylistDTO playlistDTO = playlistService.create(toDto(user), List.of(
                videoToDTO(video),
                videoToDTO(video2)
        ));

        playlistService.deletePlaylistById(playlistDTO.getId());
        assertEquals(Optional.empty(), playlistRepository.findById(playlistDTO.getId()));
    }

    @Test
    void getVideoPlaylists() {
        User user = newUser();
        user = userRepository.save(user);
        Video video = newVideo();
        video.setUser(user);
        Video video2 = newVideo();
        video2.setUser(user);

        video = videoRepository.save(video);
        video2 = videoRepository.save(video2);

        playlistService.create(toDto(user), List.of(
                videoToDTO(video),
                videoToDTO(video2)
        ));

        List<Playlist> playlistDTOS = playlistService.getVideoPlaylists(video.getId());
        assertEquals(1, playlistDTOS.size());
    }
}
