package com.example.youtubeish.playlist;

import com.example.youtubeish.BaseControllerTest;
import com.example.youtubeish.TestCreationFactory;
import com.example.youtubeish.playlist.dto.PlaylistDTO;
import com.example.youtubeish.security.dto.MessageResponse;
import com.example.youtubeish.video.dto.VideoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.youtubeish.TestCreationFactory.*;
import static com.example.youtubeish.UrlMapping.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlaylistControllerTest extends BaseControllerTest {
    @InjectMocks
    private PlaylistController playlistController;

    @Mock
    private PlaylistService playlistService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        playlistController = new PlaylistController(playlistService);
        mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();
    }

    @Test
    void getUserPlaylist() throws Exception {
        Long id = randomLong();
        List<PlaylistDTO> playlistDTOS = TestCreationFactory.listOf(PlaylistDTO.class);
        when(playlistService.getUserPlaylists(id)).thenReturn(playlistDTOS);

        ResultActions uploadVideoResult = performGetWithPathVariable(PLAYLIST + GET_USER_PLAYLIST, id);
        uploadVideoResult.andExpect(status().isOk())
                .andExpect(jsonContentToBe(playlistDTOS));
    }

    @Test
    void deletePlaylist() throws Exception {
        Long id = randomLong();
        doNothing().when(playlistService).deletePlaylistById(id);
        ResultActions uploadVideoResult = performDeleteWIthPathVariable(PLAYLIST + DELETE_PLAYLIST, id);
        uploadVideoResult.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Playlist deleted successfully!")));
    }

    @Test
    void createPlaylist() throws Exception {
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .user(newUserDto())
                .videos(TestCreationFactory.listOf(VideoDTO.class))
                .build();
        when(playlistService.create(playlistDTO.getUser(), playlistDTO.getVideos()))
                .thenReturn(playlistDTO);
        ResultActions uploadVideoResult = performPostWithRequestBody(PLAYLIST + CREATE_PLAYLIST, playlistDTO);
        uploadVideoResult.andExpect(status().isOk())
                .andExpect(jsonContentToBe(playlistDTO));
    }
}
