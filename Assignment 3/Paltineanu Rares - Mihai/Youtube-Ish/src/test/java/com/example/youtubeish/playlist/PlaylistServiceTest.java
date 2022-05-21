package com.example.youtubeish.playlist;

import com.example.youtubeish.BaseControllerTest;
import com.example.youtubeish.TestCreationFactory;
import com.example.youtubeish.playlist.dto.PlaylistDTO;
import com.example.youtubeish.playlist.mapper.PlaylistMapper;
import com.example.youtubeish.playlist.model.Playlist;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.mapper.UserMapper;
import com.example.youtubeish.video.dto.VideoDTO;
import com.example.youtubeish.video.mapper.VideoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.youtubeish.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class PlaylistServiceTest extends BaseControllerTest {

    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private VideoMapper videoMapper;

    @Mock
    private PlaylistMapper playlistMapper;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        playlistService = new PlaylistService(playlistRepository, userMapper, videoMapper, playlistMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(playlistService).build();
    }

    @Test
    void create() {
        UserDTO userDTO = newUserDto();
        List<VideoDTO> videoDTOList = List.of(
                newVideoDTO(),
                newVideoDTO(),
                newVideoDTO(),
                newVideoDTO()
        );
        Playlist playlist = Playlist.builder()
                .user(fromDto(userDTO))
                .videos(videoDTOList.stream().map(TestCreationFactory::videoFromDTO).collect(Collectors.toList()))
                .build();
        when(userMapper.fromDto(userDTO)).thenReturn(fromDto(userDTO));
        for(VideoDTO videoDTO: videoDTOList) {
            when(videoMapper.fromDto(videoDTO)).thenReturn(videoFromDTO(videoDTO));
        }
        when(playlistRepository.save(playlist)).thenReturn(playlist);
        when(playlistMapper.toDto(playlist)).thenReturn(toPlaylistDTO(playlist));
        PlaylistDTO playlistDTO = playlistService.create(userDTO, videoDTOList);
        assertNotNull(playlistDTO);
        assertEquals(playlistDTO.getUser().getUsername(), playlist.getUser().getUsername());
    }

    @Test
    void getUserPlaylists() {
        Long id = randomLong();
        List<Playlist> playlists = List.of(
                newPlaylist(),
                newPlaylist(),
                newPlaylist(),
                newPlaylist()
        );
        when(playlistRepository.getAllByUserId(id)).thenReturn(playlists);
        for(Playlist playlist: playlists) {
            when(playlistMapper.toDto(playlist)).thenReturn(toPlaylistDTO(playlist));
        }

        List<PlaylistDTO> playlistDTOS = playlistService.getUserPlaylists(id);
        assertEquals(playlists.size(), playlistDTOS.size());
        assertEquals(playlists.get(0).getVideos().get(0).getChannelTitle(), playlistDTOS.get(0).getVideos().get(0).getChannelTitle());
    }

    @Test
    void deletePlaylistId() {
        Long id = randomLong();
        doNothing().when(playlistRepository).deleteById(id);
        playlistService.deletePlaylistById(id);
    }

    @Test
    void getVideoPlaylists() {
        Long id = randomLong();
        List<Playlist> playlists = List.of(
                newPlaylist(),
                newPlaylist(),
                newPlaylist(),
                newPlaylist(),
                newPlaylist()
        );
        when(playlistRepository.findPlaylistByVideosId(id)).thenReturn(playlists);
        List<Playlist> returnedPlaylists = playlistService.getVideoPlaylists(id);
        assertEquals(playlists.size(), returnedPlaylists.size());
        assertEquals(playlists.get(0).getUser().getEmail(), returnedPlaylists.get(0).getUser().getEmail());
    }
}
