package com.project.song;

import com.project.song.model.Song;
import com.project.song.model.dto.SongDTO;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final SongMapper songMapper;

    public Song findById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found: " + id));
    }

    public List<SongDTO> findAll(){
        return songRepository.findAll().stream().map(songMapper::toDto).collect(Collectors.toList());

    }

    public List<SongDTO> findAllOnline() {

        ArrayList<SongDTO> music= new ArrayList<>();
        try {
            System.out.println("Songs online");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://spotify23.p.rapidapi.com/search/?q=%3CREQUIRED%3E&type=multi&offset=0&limit=10&numberOfTopResults=5"))
                    .header("X-RapidAPI-Host", "spotify23.p.rapidapi.com")
                    .header("X-RapidAPI-Key", "6aed63ec0cmsh6fbb66974da5728p182777jsn4fce90f2afe4")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject responseJsonObj = new JSONObject(response.body().toString());
            JSONArray songs=responseJsonObj.getJSONObject("tracks").getJSONArray("items");
            for(int i=0;i<10;++i)
            {
                String name=songs.getJSONObject(i).getJSONObject("data").getString("name");
                String artist= songs.getJSONObject(i).getJSONObject("data").getJSONObject("artists").getJSONArray("items").getJSONObject(0).getJSONObject("profile").getString("name");
                SongDTO songDTO= new SongDTO(0L,name,artist);
                music.add(songDTO);
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return music;
    }

    public byte [] getSongMp3(Long id)
    {
        System.out.println(id);
        try {
            File f = new File("resources/music.mp3");
            return Files.readAllBytes(f.toPath());
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public SongDTO create(SongDTO song) {
        return songMapper.toDto(songRepository.save(
                songMapper.fromDto(song)
        ));
    }

    public SongDTO edit(Long id, SongDTO song) {
        Song actSong = findById(id);
        actSong.setTitle(song.getTitle());
        actSong.setArtist(song.getArtist());
        return songMapper.toDto(
                songRepository.save(actSong)
        );
    }

    public void delete(Long id) {
        songRepository.deleteById(id);
    }
}
