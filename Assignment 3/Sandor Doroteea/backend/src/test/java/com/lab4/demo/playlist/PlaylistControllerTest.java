package com.lab4.demo.playlist;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.user.model.ERole.EMPLOYEE;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlaylistControllerTest extends BaseControllerTest {

    @InjectMocks
    private PlaylistController playlistController;

    @Mock
    private PlaylistService playlistService;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        playlistController = new PlaylistController(userService,playlistService);
        mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();
    }

    @Test
    void getAllPlaylists() throws Exception {
        List<PlaylistDTO> playlistDTOS = TestCreationFactory.listOf(Playlist.class);
        when(playlistService.findAll(1L)).thenReturn(playlistDTOS);

        ResultActions response = performGetWithPathVariable(PLAYLIST + PLAYLIST_ID_PART,1L);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(playlistDTOS));
    }

    @Test
    void create() throws Exception {
        PlaylistDTO playlist = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        UserListDTO userDTO = UserListDTO.builder()
                .id(1L)
                .name("doro")
                .email("doro@yahoo.com")
                .password("password1")
                .roles(Set.of(EMPLOYEE.name()))
                .playlistList(new ArrayList<>())
                .build();

        User user = User.builder()
                .id(1L)
                .username("doro")
                .email("doro@yahoo.com")
                .password("password1")
                .roles(Set.of(Role.builder().id(358).name(ERole.EMPLOYEE).build()))
                .playlistList(new ArrayList<>())
                .build();

        when(playlistService.create(playlist)).thenReturn(playlist);
        when(userService.findById(userDTO.getId())).thenReturn(user);
        when(userService.createPlaylist(userDTO.getId(),playlist)).thenReturn(userDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariables(PLAYLIST + ADD_PLAYLIST, playlist,1L);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTO));
    }

    @Test
    void update() throws Exception {
        long id = randomLong();
        PlaylistDTO playlist = PlaylistDTO.builder()
                .id(id)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistService.update(id, playlist)).thenReturn(playlist);

        ResultActions result = performPutWithRequestBodyAndPathVariables(PLAYLIST + PLAYLIST_ID_PART, playlist, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(playlist));
    }

    @Test
    void delete() throws Exception {
        PlaylistDTO playlist = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistService.create(playlist)).thenReturn(playlist);
        doNothing().when(playlistService).delete(playlist.getId());

        ResultActions response = performDeleteWithPathVariable(PLAYLIST + PLAYLIST_ID_PART, playlist.getId());
        response.andExpect(status().isOk());
    }
}