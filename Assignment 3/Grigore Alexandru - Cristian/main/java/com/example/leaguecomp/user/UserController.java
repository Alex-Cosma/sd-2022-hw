package com.example.leaguecomp.user;

import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.mail.MailService;
import com.example.leaguecomp.summoner.SummonerService;
import com.example.leaguecomp.user.dto.UserListDTO;
import com.example.leaguecomp.user.dto.UserMinimalDTO;
import com.example.leaguecomp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static com.example.leaguecomp.UrlMappings.ENTITY;
import static com.example.leaguecomp.UrlMappings.USERS;

@RestController
@CrossOrigin
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SummonerService summonerService;

    @GetMapping
    public List<UserListDTO> allUsers(){
        return userService.allUsersForList();
    }

    @PatchMapping("/edit/{id}")
    public UserListDTO edit(@PathVariable Long id, @RequestBody UserListDTO user){return userService.edit(id,user);}

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){userService.delete(id);}

    @PostMapping("/create")
    public User create(@RequestBody User user){return userService.create(user);}


    @PostMapping("/follow/{name}")
    public void followSummoner(@PathVariable String name,@RequestBody String summonerName) throws InterruptedException {
        summonerService.notifyFrontend(name);
        Thread.sleep(2000);
        userService.followSummoner(name,summonerName);
    }

    @PostMapping("/email")
    public void sendEmail(@RequestBody ChampionDTO champion) throws MessagingException, IOException {
        userService.sendMail(champion);
    }




}
