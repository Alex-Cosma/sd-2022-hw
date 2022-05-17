package com.example.youtubeish.video;

import com.example.youtubeish.security.dto.MessageResponse;
import com.example.youtubeish.user.UserService;
import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.model.Video;
import com.example.youtubeish.video.model.dto.ResultDTO;
import com.example.youtubeish.video.model.dto.UploadVideoDTO;
import com.example.youtubeish.video.model.dto.VideoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.youtubeish.UrlMapping.*;

@RestController
@RequestMapping(VIDEOS)
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final UserService userService;
    @GetMapping(GET_VIDEOS)
    public ResultDTO allVideos(@RequestParam String key,
                            @RequestParam String q,
                            @RequestParam String type,
                            @RequestParam String part,
                            @RequestParam String maxResults) {
        String url = "https://www.googleapis.com/youtube/v3/search?" +
                "key={key}&" +
                "q={q}&" +
                "part={part}&" +
                "type={type}&" +
                "maxResults={maxResults}";

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("q", q);
        params.put("type", type);
        params.put("part", part);
        params.put("maxResults", maxResults);
        return restTemplate.getForObject(url, ResultDTO.class, params);
    }

    @GetMapping(GET_USER_VIDEOS)
    public List<VideoDTO> getVideoFromUser(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        return videoService.getVideosFromUser(user.getId());
    }

    @DeleteMapping(DELETE_VIDEO)
    public ResponseEntity<?> deleteVideoById(@PathVariable Long id) {
        videoService.deleteVideoById(id);
        return ResponseEntity.ok(new MessageResponse("Video deleted successfully!"));
    }

    @PostMapping(UPLOAD_VIDEO)
    public ResponseEntity<?> uploadVideo(@RequestBody UploadVideoDTO uploadVideoDTO) {
        videoService.create(uploadVideoDTO.getVideo(), uploadVideoDTO.getUser());
        return ResponseEntity.ok(new MessageResponse("Video uploaded successfully!"));
    }
}
