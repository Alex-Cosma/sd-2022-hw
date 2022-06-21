package com.app.banking.config;

import com.app.banking.data.dto.email.MailAuth;
import com.app.banking.data.dto.email.MailTransporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

@Slf4j
@Component
public class MailConfig {

    @Value("${app.mail.enableSending}")
    private boolean enableSending;
    @Value("${app.mail.host}")
    private String host;
    @Value("${app.mail.secure}")
    private Boolean secure;
    @Value("${app.mail.service}")
    private String service;
    @Value("${app.mail.port}")
    private Integer port;
    @Value("${app.mail.user}")
    private String user;
    @Value("${app.mail.pass}")
    private String pass;
    @Value("${app.mail.sessionDebug}")
    private Boolean sessionDebug;

    public boolean isMailSendingNotEnabled() {
        return !enableSending;
    }

    public InternetAddress getFrom() {
        try {
            return new InternetAddress(user);
        } catch (AddressException exception) {
            log.error("Error while getting the InternetAddress for {}", user, exception);
            return new InternetAddress();
        }
    }

    public MailTransporter getMailTransporter() {
        MailAuth mailAuth = MailAuth.builder()
                .user(user)
                .pass(pass)
                .build();
        return MailTransporter.builder()
                .host(host)
                .secure(secure)
                .service(service)
                .port(port)
                .auth(mailAuth)
                .build();
    }

    public Session getMailSession() {
        Session result =  Session.getInstance(getSmtpProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        result.setDebug(sessionDebug);

        return result;
    }

    private Properties getSmtpProperties() {
        Properties result = new Properties();

        result.put("mail.smtp.host", host);
        result.put("mail.smtp.port", port);
        result.put("mail.smtp.ssl.enable", secure);
        result.put("mail.smtp.starttls.enable", "true");
        result.put("mail.smtp.ssl.trust", host);
        result.put("mail.transport.protocol", "smtp");
        result.put("mail.smtp.auth", "true");

        return result;
    }

}
