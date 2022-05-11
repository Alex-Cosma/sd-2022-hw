package com.example.youtubeish.video;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.naming.ldap.HasControls;

import java.util.HashMap;
import java.util.Map;

import static com.example.youtubeish.UrlMapping.GET_VIDEOS;
import static com.example.youtubeish.UrlMapping.VIDEOS;

@RestController
@RequestMapping(VIDEOS)
@RequiredArgsConstructor
public class VideoController {

    @GetMapping(GET_VIDEOS)
    public Object allVideos(@RequestParam String key,
                            @RequestParam String q,
                            @RequestParam String type,
                            @RequestParam String snippet,
                            @RequestParam String maxResults) {
        String url = "https://www.googleapis.com/youtube/v3/search?" +
                "key={key}&" +
                "q={q}&" +
                "part={snippet}&" +
                "type={type}&" +
                "maxResults={maxResults}";

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("q", q);
        params.put("type", type);
        params.put("snippet", snippet);
        params.put("maxResults", maxResults);
        return restTemplate.getForObject(url, Object.class, params);
    }
}
