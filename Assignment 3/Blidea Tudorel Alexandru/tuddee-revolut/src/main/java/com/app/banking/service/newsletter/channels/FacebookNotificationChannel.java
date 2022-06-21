package com.app.banking.service.newsletter.channels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Component
public class FacebookNotificationChannel implements NotificationChannel {

    private final RestTemplate restTemplate;
    @Value("${app.facebook.graph.baseUrl}")
    private String graphBaseUrl;
    @Value("${app.facebook.graph.tokenName}")
    private String tokenName;
    @Value("${app.facebook.page.authToken}")
    private String token;

    public FacebookNotificationChannel(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendNotification(String title, String message) {
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, getBaseHeaders());
        ResponseEntity<FacebookApiResponse> response = restTemplate.exchange(getGraphPathToFacebookAPI(title, message), HttpMethod.POST, httpEntity, FacebookApiResponse.class);
        Optional.ofNullable(response.getBody())
                .ifPresent(body -> log.info("Post successfully created with the id: {}", response.getBody().getId()));
    }

    private URI getGraphPathToFacebookAPI(String title, String message) {
        return UriComponentsBuilder.fromHttpUrl(graphBaseUrl)
                .queryParam("message", getSimpleFacebookPost(title, message))
                .queryParam(tokenName, token)
//                .encode()
                .build()
                .toUri();
    }

    private String getSimpleFacebookPost(String title, String message) {
        return String.format("*%s*\n%s", title, message);
    }

    private HttpHeaders getBaseHeaders() {
        HttpHeaders result = new HttpHeaders();

        result.set("Content-Type", "application/json");
        result.set("Accept", "application/json");

        return result;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class FacebookApiResponse {

        private String id;

    }

}
