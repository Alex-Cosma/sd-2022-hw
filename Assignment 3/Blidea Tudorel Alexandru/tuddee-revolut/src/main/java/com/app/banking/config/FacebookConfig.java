package com.app.banking.config;

import com.app.banking.data.dto.facebook.FacebookTokenConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FacebookConfig {

    @Value("${app.facebook.page.authToken}")
    private String pageAuthToken;

    public FacebookTokenConfig getFacebookTokenConfig() {
        return FacebookTokenConfig.builder()
                .token(pageAuthToken)
                .build();
    }

    public String getAuthToken() {
        return pageAuthToken;
    }

}
