package com.lab4.demo.track;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.comment.CommentService;
import com.lab4.demo.playlist.PlaylistService;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.dto.TrackDTO;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.user.model.ERole.EMPLOYEE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TrackApiControllerTest extends BaseControllerTest {

    @InjectMocks
    private TrackApiController trackApiController;

    @Mock
    private PlaylistService playlistService;

    @Mock
    private UserService userService;

    @Mock
    private CommentService commentService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        trackApiController = new TrackApiController(playlistService,userService,commentService);
        mockMvc = MockMvcBuilders.standaloneSetup(trackApiController).build();
    }

    @Test
    void allTracks() throws Exception {
//        ResultActions response = performGet(API_TRACKS);
//        response.andExpect(status().isOk());
    }

    @Test
    void addToPlaylist() throws Exception {
        List<TrackDTO> tracks = TestCreationFactory.listOf(TrackDTO.class);

        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(tracks)
                .duration(100)
                .build();
        ResultActions response = performPutWithRequestBodyAndPathVariables(API_TRACKS + PLAYLIST_ID_PART, playlistDTO, playlistDTO.getId());
        response.andExpect(status().isOk());
    }

    @Test
    void purchase() throws Exception {
        List<TrackDTO> tracks = TestCreationFactory.listOf(TrackDTO.class);
        UserListDTO userDTO = UserListDTO.builder()
                .id(1L)
                .name("doro")
                .email("doro@yahoo.com")
                .password("password1")
                .roles(Set.of(EMPLOYEE.name()))
                .purchasedTracks(tracks)
                .build();

        ResultActions response = performPutWithRequestBodyAndPathVariables(API_TRACKS + BUY_TRACK, userDTO, userDTO.getId());
        response.andExpect(status().isOk());
    }
}