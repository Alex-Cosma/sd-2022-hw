package com.app.banking.service;


import com.app.banking.config.FacebookConfig;
import com.app.banking.config.MailConfig;
import com.app.banking.data.dto.facebook.ExternalFacebookComplexElement;
import com.app.banking.data.dto.facebook.ExternalFacebookSimplePost;
import com.app.banking.data.dto.facebook.ExternalPostCurrenciesDto;
import com.app.banking.data.dto.facebook.FacebookTokenConfig;
import com.app.banking.data.dto.email.ExternalCurrencyEmailDto;
import com.app.banking.data.dto.email.ExternalSimpleEmailDto;
import com.app.banking.data.dto.email.ExternalWelcomeEmailDto;
import com.app.banking.data.dto.email.MailTransporter;
import com.app.banking.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExternalNotificationService {

    private static final String FACEBOOK_ENDPOINT = "/facebook";
    private static final String FACEBOOK_SIMPLE = FACEBOOK_ENDPOINT + "/simple";
    private static final String FACEBOOK_COMPLEX = FACEBOOK_ENDPOINT + "/complex";
    private static final String MAIL_ENDPOINT = "/mail";
    private static final String MAIL_WELCOME = MAIL_ENDPOINT + "/welcome";
    private static final String MAIL_SIMPLE = MAIL_ENDPOINT + "/simple";
    private static final String MAIL_CURRENCY_UPDATE = MAIL_ENDPOINT + "/sendCurrencyUpdate";
    private static final String CONFIG_MAIL_ENDPOINT = MAIL_ENDPOINT + "/config";
    private static final String CONFIG_FACEBOOK_ENDPOINT = "/token" + FACEBOOK_ENDPOINT;

    private final MailConfig mailConfig;
    private final FacebookConfig facebookConfig;
    private final String baseUrl;
    private final RestTemplate restTemplate;
    private final ServiceUtil serviceUtil;

    public ExternalNotificationService(MailConfig mailConfig,
                                       FacebookConfig facebookConfig,
                                       RestTemplate restTemplate,
                                       ServiceUtil serviceUtil,
                                       @Value("${app.external.baseUrl}") String baseUrl) {
        this.mailConfig = mailConfig;
        this.facebookConfig = facebookConfig;
        this.restTemplate = restTemplate;
        this.serviceUtil = serviceUtil;
        this.baseUrl = baseUrl;
        configureExternalMail();
        configureExternalFacebook();
    }

    // TODO welcome
    public void sendWelcomeEmail(ExternalWelcomeEmailDto externalMail) {
        if (mailConfig.isMailSendingNotEnabled()) {
            return;
        }
        HttpEntity<ExternalWelcomeEmailDto> httpEntity = new HttpEntity<>(externalMail, getBaseHeaders());
        restTemplate.exchange(baseUrl + MAIL_WELCOME, HttpMethod.POST, httpEntity, Void.class);
    }

    public void sendSimpleEmail(ExternalSimpleEmailDto externalMail) {
        if (mailConfig.isMailSendingNotEnabled()) {
            return;
        }
        HttpEntity<ExternalSimpleEmailDto> httpEntity = new HttpEntity<>(externalMail, getBaseHeaders());
        restTemplate.exchange(baseUrl + MAIL_SIMPLE, HttpMethod.POST, httpEntity, Void.class);
    }

    public void sendCurrencyUpdate(ExternalCurrencyEmailDto externalMail) {
        if (mailConfig.isMailSendingNotEnabled()) {
            return;
        }
        HttpEntity<ExternalCurrencyEmailDto> httpEntity = new HttpEntity<>(externalMail, getBaseHeaders());
        restTemplate.exchange(baseUrl + MAIL_CURRENCY_UPDATE, HttpMethod.POST, httpEntity, Void.class);
    }

    public void postCurrencyUpdate(Map<String, Double> currencies) {
        HttpEntity<ExternalPostCurrenciesDto> httpEntity = new HttpEntity<>(getFacebookPostCurrencies(currencies), getBaseHeaders());
        restTemplate.exchange(baseUrl + FACEBOOK_COMPLEX, HttpMethod.POST, httpEntity, Void.class);
    }

    public void postSimpleMessage(String message) {
        ExternalFacebookSimplePost body = ExternalFacebookSimplePost.builder()
                .message(message)
                .build();
        HttpEntity<ExternalFacebookSimplePost> httpEntity = new HttpEntity<>(body, getBaseHeaders());
        restTemplate.exchange(baseUrl + FACEBOOK_SIMPLE, HttpMethod.POST, httpEntity, Void.class);
    }

    private void configureExternalMail() {
        HttpEntity<MailTransporter> httpEntity = new HttpEntity<>(mailConfig.getMailTransporter(), getBaseHeaders());
        restTemplate.exchange(baseUrl + CONFIG_MAIL_ENDPOINT, HttpMethod.POST, httpEntity, Void.class);
    }

    private void configureExternalFacebook() {
        HttpEntity<FacebookTokenConfig> httpEntity = new HttpEntity<>(facebookConfig.getFacebookTokenConfig(), getBaseHeaders());
        restTemplate.exchange(baseUrl + CONFIG_FACEBOOK_ENDPOINT, HttpMethod.POST, httpEntity, Void.class);
    }

    private HttpHeaders getBaseHeaders() {
        HttpHeaders result = new HttpHeaders();

        result.set("Content-Type", "application/json");
        result.set("Accept", "application/json");

        return result;
    }

    private ExternalPostCurrenciesDto getFacebookPostCurrencies(Map<String, Double> currencies) {
        ExternalFacebookComplexElement element = ExternalFacebookComplexElement.builder()
                .message("Today currencies at " + serviceUtil.formatDateTimeToString(LocalDateTime.now()) + ": ")
                .list(
                        currencies.entrySet().stream()
                                .map(entry -> entry.getKey() + " " + entry.getValue() + ": ")
                                .collect(Collectors.toList())
                )
                .ordered(false)
                .build();
        return ExternalPostCurrenciesDto.builder()
                .elements(List.of(element))
                .build();
    }

}
