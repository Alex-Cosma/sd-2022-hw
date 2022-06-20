package com.rdaniel.sd.project.common.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

  private final JavaMailSender mailSender;

  public void sendEmail(String email, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("reckerthdaniel@gmail.com");
    message.setTo(email);
    message.setSubject(subject);
    message.setText(body);

    mailSender.send(message);

    log.info("Email sent to {}", email);
  }
}
