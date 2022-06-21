package com.app.banking.service;

import java.util.List;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import com.app.banking.config.MailConfig;
import com.app.banking.data.dto.email.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailConfig mailConfig;

    public void sendEmail(EmailDto email, String recipient) {
        if (mailConfig.isMailSendingNotEnabled()) {
            return;
        }
        try {
            Message message = new MimeMessage(mailConfig.getMailSession());

            message.setFrom(mailConfig.getFrom());
            message.addRecipient(Message.RecipientType.TO, getRecipientAddress(recipient));
            message.setSubject(email.getSubject());
            message.setText(email.getContent());

            Transport.send(message);
        } catch (MessagingException exception) {
            log.error("An error occurred while sending the email.", exception);
        }
    }

    public void sendEmail(EmailDto email, List<String> recipients) {
        if (mailConfig.isMailSendingNotEnabled()) {
            return;
        }
        try {
            Message message = new MimeMessage(mailConfig.getMailSession());

            message.setFrom(mailConfig.getFrom());
            message.addRecipients(Message.RecipientType.TO, getRecipientsAddresses(recipients));
            message.setSubject(email.getSubject());
            message.setText(email.getContent());

            Transport.send(message);
        } catch (MessagingException exception) {
            log.error("An error occurred while sending the email.", exception);
        }
    }

    private Address[] getRecipientsAddresses(List<String> recipients) {
        return (Address[]) recipients.stream().map(this::getRecipientAddress).toArray();
    }

    private Address getRecipientAddress(String recipient) {
        try {
            return new InternetAddress(recipient);
        } catch (AddressException exception) {
            log.error("Error while getting the InternetAddress for {}", recipient, exception);
            return new InternetAddress();
        }
    }

}
