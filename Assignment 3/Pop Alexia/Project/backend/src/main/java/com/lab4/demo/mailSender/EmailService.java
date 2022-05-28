package com.lab4.demo.mailSender;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public String sendMail(String mail) throws MessagingException {

        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("NEW QUIZZ");
        helper.setText("A new quizz has been added , come and boost up your knowledge");
        helper.setTo(mail);
        javaMailSender.send(mimeMessage);
        return "Mail sent";
    }
}
