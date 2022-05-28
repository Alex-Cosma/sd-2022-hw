package com.lab4.demo.track;


import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.playlist.PlaylistRepository;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.dto.TrackDTO;
import com.lab4.demo.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class TrackServiceTest {
    @InjectMocks
    private TrackService trackService;

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private TrackMapper trackMapper;

    @Mock
    private PlaylistRepository playlistRepository;


    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trackService = new TrackService(userRepository,playlistRepository,trackRepository, trackMapper);
    }

    @Test
    void findAll() {
        List<Track> tracks = TestCreationFactory.listOf(Track.class);
        when(trackRepository.findAll()).thenReturn(tracks);

        List<TrackDTO> all = trackService.findAll();

        Assertions.assertEquals(tracks.size(), all.size());
    }

    @Test
    void create(){
        TrackDTO track1 = TrackDTO.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        when(trackMapper.toDto(trackRepository.save(trackMapper.fromDto(track1)))).thenReturn(track1);
        when(trackService.create(track1)).thenReturn(track1);
        TrackDTO track2 = trackService.create(track1);
        Assertions.assertEquals(track1, track2);
    }
    
    @Test
    void edit(){
        TrackDTO trackDTO = TrackDTO.builder()
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
        when(trackMapper.fromDto(trackDTO)).thenReturn(track);
        when(trackMapper.toDto(track)).thenReturn(trackDTO);

        when(trackRepository.save(track)).thenReturn(track);
        when(trackRepository.findById(track.getId())).thenReturn(Optional.of(track));

        track.setTitle("Edited Title");

        TrackDTO edited = trackService.edit(track.getId(),trackMapper.toDto(track));
        when(trackRepository.save(track)).thenReturn(track);
        Assertions.assertEquals(edited.getId(), track.getId());

    }

    @Test
    void delete(){
        Track track = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        when(trackRepository.save(track)).thenReturn(track);
        when(trackRepository.existsById(track.getId())).thenReturn(false);

        trackService.delete(track.getId());
        Assertions.assertFalse(trackRepository.existsById(track.getId()));
    }

    @Test
    void findById(){
        Track track = Track.builder()
                .id(1L)
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        when(trackRepository.findById(track.getId())).thenReturn(Optional.of(track));
        Track trackDTO = trackService.getTrackById(track.getId());

        Assertions.assertEquals(track.getTitle(),trackDTO.getTitle());
    }

    @Test
    void findByTitle(){
        Track track = Track.builder()
                .id(1L)
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        when(trackRepository.findByTitle(track.getTitle())).thenReturn(Optional.of(track));
        Optional<Track> trackDTO = trackService.findByTitle(track.getTitle());

        Assertions.assertEquals(track,trackDTO.get());
    }

}