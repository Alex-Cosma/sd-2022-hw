package com.app.banking.controller;

import com.app.banking.data.dto.model.NewsletterDto;
import com.app.banking.service.newsletter.NewsletterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.app.banking.URLMapping.NEWSLETTER;

@RestController
@RequiredArgsConstructor
@RequestMapping(NEWSLETTER)
@CrossOrigin(originPatterns = "http://localhost:*")
public class NewsletterController {

    private final NewsletterService newsletterService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void sendNews(@RequestBody NewsletterDto newsletter) {
        newsletterService.sendNewsletter(newsletter.getTitle(), newsletter.getContent());
    }

}
