package com.policeSection.mail;

import com.policeSection.Mail.MailSendigService;
import com.policeSection.driver.DriverService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootTest
public class MailSendingServiceTest {
    @Autowired
    private MailSendigService mailSendigService;
    @Test
    void getJavaMailSender()
    {
        JavaMailSender javaMailSender = mailSendigService.getJavaMailSender();
        Assertions.assertNotNull(javaMailSender);
    }
}
