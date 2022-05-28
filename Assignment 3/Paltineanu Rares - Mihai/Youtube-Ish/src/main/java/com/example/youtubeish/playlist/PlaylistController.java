package com.example.youtubeish.playlist;

import com.example.youtubeish.playlist.dto.PlaylistDTO;
import com.example.youtubeish.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public PlaylistDTO createPlaylist(@RequestBody PlaylistDTO playlist) {
        return playlistService.create(playlist.getUser(), playlist.getVideos());
    }

    @DeleteMapping(DELETE_PLAYLIST)
    public ResponseEntity<?> deletePlaylist(@PathVariable  Long id) {
        this.playlistService.deletePlaylistById(id);
        return ResponseEntity.ok(new MessageResponse("Playlist deleted successfully!"));
    }
}
