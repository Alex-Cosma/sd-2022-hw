package com.project.song;

import com.project.TestCreationFactory;

import com.project.song.model.Song;
import com.project.song.model.dto.SongDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;

class SongServiceTest {

    @InjectMocks
    private SongService songService;

    @Mock
    private SongRepository songRepository;

    @Mock
    private SongMapper songMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        songService = new SongService(songRepository, songMapper);
    }

    @Test
    void findAll() {
        List<Song> songs = TestCreationFactory.listOf(Song.class);
        when(songRepository.findAll()).thenReturn(songs);

        List<SongDTO> all = songService.findAll();

        Assertions.assertEquals(songs.size(), all.size());
    }


}