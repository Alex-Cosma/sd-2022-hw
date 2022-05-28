package com.lab4.demo.track;

import com.lab4.demo.comment.CommentService;
import com.lab4.demo.comment.model.dto.CommentDTO;
import com.lab4.demo.playlist.PlaylistService;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.model.dto.TrackDTO;
import com.lab4.demo.user.UserNotFoundException;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserListDTO;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@Validated
@RestController
@RequestMapping(API_TRACKS)
@RequiredArgsConstructor
public class TrackApiController {

    private final PlaylistService playlistService;
    private final UserService userService;
    private final CommentService commentService;

    private List<TrackDTO> searchTracks(){
        HttpResponse<String> response = Unirest.get("https://deezerdevs-deezer.p.rapidapi.com/playlist/3155776842")
                .header("X-RapidAPI-Host", "deezerdevs-deezer.p.rapidapi.com")
                .header("X-RapidAPI-Key", "b0a6df27edmsha84d7ac00c2bd55p1f21bfjsn857a47e91b7e")
                .asString();
        List<TrackDTO> tracks = new ArrayList<>();
        final JSONObject obj = new JSONObject(response.getBody());
        JSONObject data1 = obj.getJSONObject("tracks");
        JSONArray data = data1.getJSONArray("data");
        int n = data.length();

        for (int i = 0; i < n; ++i) {
            JSONObject song = data.getJSONObject(i);
            JSONObject artistJson = song.getJSONObject("artist");
            JSONObject albumJson = song.getJSONObject("album");
            TrackDTO track = TrackDTO.builder()
                    .id((long) song.getInt("id"))
                    .title(song.getString("title"))
                    .link(song.getString("link"))
                    .preview(song.getString("preview"))
                    .duration(song.getInt("duration"))
                    .explicit_lyrics(song.getBoolean("explicit_lyrics"))
                    .artist(artistJson.getString("name"))
                    .album(albumJson.getString("title"))
                    .build();
            tracks.add(track);
        }
        return tracks;
    }

    @GetMapping
    public List<TrackDTO> allTracks() {
        return searchTracks();
    }

    @PutMapping(PLAYLIST_ID_PART)
    public PlaylistDTO addToPlaylist(@PathVariable Long id, @RequestBody TrackDTO track){
        return playlistService.addTrackInPlaylist(id,track);
    }

    @PutMapping(BUY_TRACK)
    public UserListDTO purchase(@PathVariable Long id, @RequestBody TrackDTO track) throws UserNotFoundException {
        return userService.buyTrack(id,track);
    }

    @PutMapping(TRACK_ID_PART+"/comments")
    public CommentDTO createComment(@RequestBody CommentDTO comment){
        return commentService.save(comment);
    }

    //    public List<TrackDTO> searchTracks(String query){
//       // query="ariana";
//        RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
//        HttpClientBuilder customizedClientBuilder =
//                HttpClients.custom().setDefaultRequestConfig(customizedRequestConfig);
//        CloseableHttpClient client = customizedClientBuilder.build(); // customized client,
//        HttpResponse<String> response = Unirest.get("https://deezerdevs-deezer.p.rapidapi.com/search?q=" + query)
//                .header("X-RapidAPI-Host", "deezerdevs-deezer.p.rapidapi.com")
//                .header("X-RapidAPI-Key", "b0a6df27edmsha84d7ac00c2bd55p1f21bfjsn857a47e91b7e")
//                .asString();
//        List<TrackDTO> tracks = new ArrayList<>();
//        final JSONObject obj = new JSONObject(response.getBody());
//        JSONArray data = obj.getJSONArray("data");
//        int n = data.length();
//
//        for (int i = 0; i < n; ++i) {
//            JSONObject song = data.getJSONObject(i);
//            JSONObject artistJson = song.getJSONObject("artist");
//            JSONObject albumJson = song.getJSONObject("album");
//            TrackDTO track = TrackDTO.builder()
//                    .id((long) song.getInt("id"))
//                    .title(song.getString("title"))
//                    .link(song.getString("link"))
//                    .preview(song.getString("preview"))
//                    .duration(song.getInt("duration"))
//                    .explicit_lyrics(song.getBoolean("explicit_lyrics"))
//                    .artist(artistJson.getString("name"))
//                    .album(albumJson.getString("title"))
//                    .build();
//            tracks.add(track);
//        }
//        return tracks;
//    }

}
