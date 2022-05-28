package com.lab4.demo.mailSender;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

import static com.lab4.demo.UrlMapping.EMAIL;

@RestController
@RequestMapping(EMAIL)
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping
    public String sendMail() {
        try {
            emailService.sendMail("p70987742@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Email sent";
    }

}
