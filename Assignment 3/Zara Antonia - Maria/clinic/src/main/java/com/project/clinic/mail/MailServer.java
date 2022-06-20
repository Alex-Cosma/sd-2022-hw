package com.project.clinic.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.Properties;

@Component
public class MailServer {

        public JavaMailSender getJavaMailSender() {
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                mailSender.setHost("smtp.mail.yahoo.com");
                mailSender.setPort(587);

                mailSender.setUsername("zaraantonia@yahoo.com");
                mailSender.setPassword("xmxnlllfbydokbik");

                Properties props = mailSender.getJavaMailProperties();
                props.put("mail.smtp.ssl.trust", "smtp.mail.yahoo.com");
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.debug", "true");

                return mailSender;
        }
}
