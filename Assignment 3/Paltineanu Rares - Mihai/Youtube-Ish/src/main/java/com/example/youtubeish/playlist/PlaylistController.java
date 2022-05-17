package com.example.youtubeish.playlist;

import com.example.youtubeish.playlist.model.Playlist;
import com.example.youtubeish.playlist.model.dto.CreatePlaylistDTO;
import com.example.youtubeish.playlist.model.dto.PlaylistDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.youtubeish.UrlMapping.*;

@RestController
@RequestMapping(PLAYLIST)
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    @GetMapping(GET_USER_PLAYLIST)
    public List<PlaylistDTO> getUserPlaylist(@PathVariable  Long id) {
        return new ArrayList<>();
    }

    @PostMapping(CREATE_PLAYLIST)
    public void createPlaylist(@RequestBody PlaylistDTO playlist) {
        playlistService.create(playlist.getUser(), playlist.getVideos());
    }
}
