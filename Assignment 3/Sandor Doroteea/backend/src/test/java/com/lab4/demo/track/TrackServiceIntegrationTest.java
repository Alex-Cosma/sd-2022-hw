package com.lab4.demo.track;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.dto.TrackDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TrackServiceIntegrationTest {
    @Autowired
    private TrackService trackService;

    @Autowired
    private TrackRepository trackRepository;

    @BeforeEach
    void setUp() {
        trackRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Track> tracks = TestCreationFactory.listOf(Track.class);
        trackRepository.saveAll(tracks);

        List<TrackDTO> all = trackService.findAll();

        Assertions.assertEquals(tracks.size(), all.size());
    }

    @Test
    void createTest(){
        TrackDTO track = TrackDTO.builder()
                .title("title song2")
                .link("link2")
                .preview("preview2")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name")
                .album("title album")
                .build();
        TrackDTO resTrack = trackService.create(track);
        Assertions.assertNotNull(resTrack);
        Assertions.assertEquals(track.getTitle(),resTrack.getTitle());
    }

    @Test
    void editTest(){
        TrackDTO track = TrackDTO.builder()
                .title("title song2")
                .link("link2")
                .preview("preview2")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name")
                .album("title album")
                .build();
        TrackDTO trackDTO =  trackService.create(track);
        trackDTO.setTitle("Mica printesa");
        TrackDTO resTrack = trackService.edit(trackDTO.getId(),trackDTO);
        Assertions.assertNotNull(resTrack);
        Assertions.assertEquals(trackDTO.getTitle(),resTrack.getTitle());
    }

    @Test
    void deleteTest(){
        TrackDTO track = TrackDTO.builder()
                .title("title song2")
                .link("link2")
                .preview("preview2")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name")
                .album("title album")
                .build();
        TrackDTO trackDTO =  trackService.create(track);
        trackService.delete(trackDTO.getId());
        List<TrackDTO> all = trackService.findAll();
        Assertions.assertEquals(0, all.size());
    }
}
