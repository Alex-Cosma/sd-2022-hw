package com.lab4.demo.playlist;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.TrackMapper;
import com.lab4.demo.track.TrackService;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.dto.TrackDTO;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class PlaylistServiceTest {

    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlaylistMapper playlistMapper;

    @Mock
    private TrackMapper trackMapper;

    @Mock
    private PlaylistRepository  playlistRepository;

    @Mock
    private TrackService trackService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playlistService = new PlaylistService(userRepository,playlistRepository,playlistMapper,trackService,trackMapper);
    }

    @Test
    void findAll() {
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        List<Playlist> playlists = new ArrayList<>();
        playlists.add(playlist);


        User user = User.builder()
                .email("ana@gmail.com")
                .password("sdfsg")
                .username("dasfdsv")
                .playlistList(playlists)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(playlistMapper.toDTO(playlist)).thenReturn(playlistDTO);
        when(playlistRepository.findAll()).thenReturn(playlists);

        List<PlaylistDTO> all = playlistService.findAll(1L);

        Assertions.assertEquals(playlists.size(), all.size());
    }

    @Test
    void find(){
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistRepository.findById(playlistDTO.getId())).thenReturn(Optional.of(playlist));

        Playlist playlist1 = playlistService.findById(playlistDTO.getId());

        Assertions.assertEquals(playlistDTO.getTitle(),playlist1.getTitle());
    }

    @Test
    void create() {
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);
        when(playlistRepository.save(playlist)).thenReturn(playlist);

        PlaylistDTO playlistDTO1 = playlistService.create(playlistDTO);

        Assertions.assertEquals(playlistDTO,playlistDTO1);
    }

    @Test
    void update() {
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistRepository.findById(playlist.getId())).thenReturn(Optional.of(playlist));
        when(playlistMapper.toDTO(playlistRepository.save(playlist))).thenReturn(playlistDTO);


        PlaylistDTO newPlaylist = playlistService.update(playlist.getId(), playlistDTO);
        Assertions.assertEquals(playlistDTO,newPlaylist);
    }

    @Test
    void delete() {
        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistRepository.save(playlist)).thenReturn(playlist);
        when(playlistRepository.existsById(playlist.getId())).thenReturn(false);

        playlistService.delete(playlist.getId());
        Assertions.assertFalse(playlistRepository.existsById(playlist.getId()));
    }

    @Test
    void addTrack(){
        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);

        PlaylistDTO playlistDTO1 = playlistService.create(playlistDTO);
        System.out.println(playlistDTO1.getTitle());

        TrackDTO track1 = TrackDTO.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        Track track = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        playlistDTO1.getTracks().add(track1);

        when(playlistRepository.findById(playlistDTO.getId())).thenReturn(Optional.ofNullable(playlist));
        when(trackService.create(track1)).thenReturn(track1);
        when(trackMapper.fromDto(track1)).thenReturn(track);
        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);
        PlaylistDTO playlistDTO2 = playlistService.addTrackInPlaylist(playlistDTO1.getId(),track1);

        System.out.println(playlistDTO2.getTracks().get(0).getTitle());
        Assertions.assertEquals(playlistDTO2.getTracks().get(0).getTitle(), track1.getTitle());

    }
}