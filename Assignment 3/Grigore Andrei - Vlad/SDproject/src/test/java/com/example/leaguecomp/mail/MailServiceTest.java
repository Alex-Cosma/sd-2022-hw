package com.example.leaguecomp.mail;

import com.example.leaguecomp.champion.ChampionRepository;
import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.user.model.ERole;
import com.example.leaguecomp.user.model.Role;
import com.example.leaguecomp.user.model.User;
import org.h2.engine.UserBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MailServiceTest {
    @Autowired
    private ChampionRepository championRepository;

    @Test
    void sendMail() throws MessagingException, IOException {

        MailService mailService = new MailService();
        Role role = new Role();
        Champion champion = championRepository.findAll().get(0);
        role.setRole(ERole.ADMINISTRATOR);
        User user = User.builder()
                .email("grigoreandreivlad@gmail.com")
                .username("adolf")
                .password("Masterchief1234")
                .roles(Set.of(role))
                .build();
        mailService.sendEmail(champion,user);
    }
}