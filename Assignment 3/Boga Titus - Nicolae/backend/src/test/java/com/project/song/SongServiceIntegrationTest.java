package com.project.song;

import com.project.TestCreationFactory;
import com.project.song.model.Song;
import com.project.song.model.dto.SongDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@SpringBootTest
class SongServiceIntegrationTest {

    @Autowired
    private SongService songService;

    @Autowired
    private SongRepository songRepository;

    @BeforeEach
    void setUp() {
        songRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Song> songs = TestCreationFactory.listOf(Song.class);
        songRepository.saveAll(songs);

        List<SongDTO> all = songService.findAll();

        Assertions.assertEquals(songs.size(), all.size());
    }


    @Test
    void findById(){
        List<Song> songs = TestCreationFactory.listOf(Song.class);
        songRepository.saveAll(songs);


        List<SongDTO> fromInsert= songService.findAll();
        Song fromDb= songService.findById(fromInsert.get(0).getId());

        Assertions.assertEquals(fromDb.getTitle(),fromInsert.get(0).getTitle());
        Assertions.assertEquals(fromDb.getArtist(),fromInsert.get(0).getArtist());
    }




    @Test
    void create()
    {
        List<Song> songs = TestCreationFactory.listOf(Song.class);
        songRepository.saveAll(songs);

        Song newSong= TestCreationFactory.newSong();
        songRepository.save(newSong);

        List<SongDTO> fromInsert= songService.findAll();

        Assertions.assertEquals(newSong.getTitle(),fromInsert.get(fromInsert.size()-1).getTitle());
        Assertions.assertEquals(newSong.getArtist(),fromInsert.get(fromInsert.size()-1).getArtist());

    }


    @Test
    void edit(){
        List<Song> songs = TestCreationFactory.listOf(Song.class);
        songRepository.saveAll(songs);

        List<SongDTO> fromInsert= songService.findAll();

        SongDTO last = fromInsert.get(fromInsert.size()-1);
        Song song = Song.builder().id(last.getId()).title("New").artist("New").build();

        songRepository.save(song);
        Song fromDb= songService.findById(last.getId());


        Assertions.assertEquals(song.getTitle(),fromDb.getTitle());
        Assertions.assertEquals(song.getArtist(),fromDb.getArtist());

    }

    @Test
    void delete()
    {
        List<Song> songs = TestCreationFactory.listOf(Song.class);
        songRepository.saveAll(songs);

        List<SongDTO> fromInsert= songService.findAll();

        SongDTO last = fromInsert.get(fromInsert.size()-1);

        songRepository.deleteById(last.getId());

        List<SongDTO> fromInsert2= songService.findAll();
        SongDTO last2 = fromInsert2.get(fromInsert2.size()-1);

        Assertions.assertNotEquals(fromInsert.size(),fromInsert2.size());
        Assertions.assertNotEquals(last2.getId(),last.getId());

    }

    @Test
    void getSongMp3()
    {
        try {
            File f = new File("resources/music.mp3");
            Files.readAllBytes(f.toPath());
        } catch (Exception e)
        {
            System.out.println(e);
            assert false;
        }
    }
}