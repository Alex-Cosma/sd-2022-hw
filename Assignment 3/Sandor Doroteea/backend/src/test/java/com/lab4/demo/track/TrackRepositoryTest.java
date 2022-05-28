package com.lab4.demo.track;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.track.model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrackRepositoryTest {

    @Autowired
    private TrackRepository trackRepository;

    @BeforeEach
    public void beforeEach() {
        trackRepository.deleteAll();
    }

    @Test
    public void testMock() {
        Track trackSaved = trackRepository.save(Track.builder()
                .id(1L)
                .title("title song")
                .link("link")
                .preview("preview")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name")
                .album("title album")
                .build());
        assertNotNull(trackSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            trackRepository.save(Track.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Track> tracks = TestCreationFactory.listOf(Track.class);
        trackRepository.saveAll(tracks);
        List<Track> all = trackRepository.findAll();
        assertEquals(tracks.size(), all.size());
    }

    @Test
    public void deleteTrack() {
        Track track1 = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        trackRepository.save(track1);
        trackRepository.delete(track1);
        assertEquals(0, trackRepository.findAll().size());
    }

    @Test
    public void editTrack(){
        Track track1 = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        trackRepository.save(track1);
        String oldTitle = track1.getTitle();
        track1.setTitle("new title");
        trackRepository.save(track1);
        assertNotEquals(oldTitle, trackRepository.findById(track1.getId()).get().getTitle());

    }

    @Test
    public void create(){
        Track track1 = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();

        trackRepository.save(track1);
        assertEquals(track1, trackRepository.findById(track1.getId()).get());
    }

    @Test
    public void findByTitle(){
        Track track1 = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();

        Track track = trackRepository.save(track1);

        assertEquals(track, trackRepository.findById(track.getId()).get());
    }

}