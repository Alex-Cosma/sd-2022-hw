package com.example.leaguecomp.mail;

import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.model.Item;
import com.example.leaguecomp.rune.model.Rune;
import com.example.leaguecomp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailService {

        private JavaMailSender mailSender;
        SimpleMailMessage msg;
        public void sendEmail(Champion champion, User user) throws AddressException, MessagingException, IOException{
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            Properties properties = new Properties();
            properties.put("mail.smtp.auth","true");
            properties.put("mail.smtp.starttls.enable","true");
            properties.put("mail.smtp.host","smtp.gmail.com");
            properties.put("mail.smtp.port","587");

            mailSender.setUsername("grigoreandreivlad@gmail.com");
            mailSender.setPassword("MasterChief12");
            mailSender.setJavaMailProperties(properties);

            String from = "HoboHarry_WallCrawler@gmail.com";
            String to = user.getEmail();

            msg = new SimpleMailMessage();

            msg.setFrom(from);
            msg.setTo(to);
            msg.setSubject("Build For Champion: "+ champion.getName()+" changed.");
            StringBuilder sb = new StringBuilder();
            sb.append("new Build:").append("\n");
            List<Item> items = champion.getBuild();
            Set<Rune> runes = champion.getRunes();
            for(Item item: items){
                sb.append(item.getName()).append("\n");
            }
            for(Rune rune: runes){
                sb.append(rune.getName()).append("\n");
            }
            msg.setText(sb.toString());
            mailSender.send(msg);
        }
}
