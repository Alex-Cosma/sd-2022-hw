package com.example.youtubeish.video;

import com.example.youtubeish.user.dto.UserDetailsImpl;
import com.example.youtubeish.video.model.Video;
import com.example.youtubeish.video.model.dto.ResultDTO;
import com.example.youtubeish.video.model.dto.UploadVideoDTO;
import com.example.youtubeish.video.model.dto.VideoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.naming.ldap.HasControls;

import java.util.HashMap;
import java.util.Map;

import static com.example.youtubeish.UrlMapping.*;

@RestController
@RequestMapping(VIDEOS)
@RequiredArgsConstructor
public class VideoController {

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

    @PostMapping(UPLOAD_VIDEO)
    public VideoDTO uploadVideo(@RequestBody UploadVideoDTO uploadVideoDTO) {

        return null;
    }
}
