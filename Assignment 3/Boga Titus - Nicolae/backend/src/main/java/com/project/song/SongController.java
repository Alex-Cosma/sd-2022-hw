package com.project.song;

import com.project.song.model.dto.SongDTO;
import com.project.UrlMapping;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static com.project.UrlMapping.*;

@RestController
@RequestMapping(UrlMapping.SONGS)
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping
    public List<SongDTO> allSongs() {
        return songService.findAll();
    }

    @GetMapping(SONGS_ONLINE)
    public List<SongDTO> allSongsOnline()  {
        return songService.findAllOnline();
    }

    @PostMapping
    public SongDTO create(@RequestBody SongDTO song) {
        return songService.create(song);
    }

    @PatchMapping(SONG_ENTITY)
    public SongDTO edit(@PathVariable Long id, @RequestBody SongDTO song) {
        return songService.edit(id, song);
    }

    @DeleteMapping(SONG_ENTITY)
    public void delete(@PathVariable Long id) {
        songService.delete(id);
    }

    @GetMapping(SONG_ENTITY2)
    public byte [] getMp3(@PathVariable Long id2) {
        return songService.getSongMp3(id2);
    }
}
