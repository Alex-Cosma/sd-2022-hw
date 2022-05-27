package com.lab4.demo.email;

import com.lab4.demo.investment.model.Investment;
import com.lab4.demo.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Component
public class EmailServiceImpl {
    public void sendMail(User user) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "tudordan25@gmail.com";
        String password = "gevcwisvsazsudmv";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, user.getEmail(), user);
        System.out.println("Preparing your message....");
        System.out.println(user.getEmail());
        Transport.send(message);
        System.out.println("Message sent succesfully!!");
    }

    private Message prepareMessage(Session session, String myAccountEmail, String recipient, User user) {
        try {
            String messageText = "";
            StringBuilder messages=new StringBuilder();
            for(Investment investment : user.getInvestments()){
                messages.append(investment.getSymbol()).append(" ").append(investment.getQuantity()).append("\n");
            }
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Your investments");
            String toShow=messages.toString();
            message.setText("Invesments:\n"+ toShow);
            return message;
        } catch (Exception ex) {
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }



}
