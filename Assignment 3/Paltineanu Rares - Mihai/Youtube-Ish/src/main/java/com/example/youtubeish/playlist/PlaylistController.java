package com.example.youtubeish.playlist;

import com.example.youtubeish.playlist.dto.PlaylistDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.youtubeish.UrlMapping.*;

@RestController
@RequestMapping(PLAYLIST)
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    @GetMapping(GET_USER_PLAYLIST)
    public List<PlaylistDTO> getUserPlaylist(@PathVariable  Long id) {
        return playlistService.getUserPlaylists(id);
    }

    @PostMapping(CREATE_PLAYLIST)
    public void createPlaylist(@RequestBody PlaylistDTO playlist) {
        playlistService.create(playlist.getUser(), playlist.getVideos());
    }
}
