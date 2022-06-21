package com.app.banking.scheduler;

import com.app.banking.client.CurrencyClient;
import com.app.banking.data.dto.email.ExternalCurrencyEmailDto;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.repo.UserRepository;
import com.app.banking.service.ExternalNotificationService;
import com.app.banking.service.OffersService;
import com.app.banking.service.newsletter.NewsletterService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewsScheduler {

    private static final String OFFERS_TITLE = "Checkout these offers ";

    private final ExternalNotificationService externalNotificationService;
    private final CurrencyClient currencyClient;
    private final UserRepository userRepository;
    private final OffersService offersService;
    private final NewsletterService newsletterService;

    @Scheduled(cron = "00 00/3 * * * *")
    public void notifyAboutCurrencies() {
        Map<String, Double> currencies = currencyClient.getCurrencies();

        externalNotificationService.postCurrencyUpdate(currencies);
        externalNotificationService.sendCurrencyUpdate(ExternalCurrencyEmailDto.builder()
                        .currencies(currencies)
                        .recipients(getAllEmails())
                .build());
    }

    @Scheduled(cron = "00 00 12 * * *")
    public void notifyAboutOffers() {
        String message = getAvailableOffersAsMessage();
        newsletterService.sendNewsletter(OFFERS_TITLE, message);
    }

    public List<String> getAllEmails() {
        return userRepository.findAll().stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    public String getAvailableOffersAsMessage() {
        return offersService.getAvailableOffers().stream()
                .map(offer -> String.format(" - %s: %s", offer.getName(), offer.getDescription()))
                .collect(Collectors.joining("\n"));
    }

}
