package com.lab4.demo.mailSender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MailSenderServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        MimeMessage mimeMessage = new MimeMessage((Session)null);
        javaMailSender = mock(JavaMailSender.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        MockitoAnnotations.openMocks(this);
        emailService = new EmailService(javaMailSender);
    }
    @Test
    void sendMail() throws Exception {
        String message = "Mail sent";

        when(javaMailSender.createMimeMessage()).thenReturn(new javax.mail.internet.MimeMessage((Session) null));
        String message2 = emailService.sendMail("p70987742@gmail.com");

        assertEquals(message2, message);
    }
}
