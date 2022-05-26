package com.example.leaguecomp.user;

import com.example.leaguecomp.champion.ChampionRepository;
import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.mapper.ChampionMapper;
import com.example.leaguecomp.security.AuthService;
import com.example.leaguecomp.security.dto.SignupRequest;
import com.example.leaguecomp.summoner.RegionRepository;
import com.example.leaguecomp.summoner.SummonerRepository;
import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import com.example.leaguecomp.summoner.model.Summoner;
import com.example.leaguecomp.user.dto.UserListDTO;
import com.example.leaguecomp.user.dto.UserMinimalDTO;
import com.example.leaguecomp.user.model.ERole;
import com.example.leaguecomp.user.model.Role;
import com.example.leaguecomp.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService service;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private ChampionMapper championMapper;

    @Test
    void allUsersMinimal() {
        List<UserMinimalDTO> users = service.allUsersMinimal();
        assertNotNull(users);
    }

    @Test
    void allUsersForList() {
        List<UserListDTO> users = service.allUsersForList();
        assertNotNull(users);
    }

    @Test
    void findById() {
       User user = service.findById(1L);
       assertNotNull(user);
    }
    @Test
    void findByName() {
        assertNotNull(service.findByName("adolf2"));
    }


    @Test
    void edit() {
        Region region = new Region();
        region.setRegion(ERegion.EUROPE_NORTH_EAST);
        regionRepository.save(region);
        Summoner summoner = Summoner.builder()
                .name("name")
                .league("IRON")
                .region(region)
                .build();
        summonerRepository.save(summoner);
        User user = repository.findByUsername("adolf3").orElseThrow(() -> new EntityNotFoundException("user not found"));
        UserListDTO editUser = UserListDTO.builder()
                .email("newEmail")
                .name("name5")
                .roles(Set.of("ADMINISTRATOR"))
                .summoners(Set.of(summoner))
                .build();
        assertNotNull(service.edit(user.getId(), editUser));
        regionRepository.delete(region);
        summonerRepository.delete(summoner);
    }

    @Test
    void followSummoner() {
        Region region = new Region();
        region.setRegion(ERegion.EUROPE_NORTH_EAST);
        regionRepository.save(region);
        Summoner summoner = Summoner.builder()
                .name("name")
                .league("IRON")
                .region(region)
                .build();
        summonerRepository.save(summoner);
        User user = repository.findByUsername("adolf2").orElseThrow(() -> new EntityNotFoundException("user not found"));
        service.followSummoner(user.getUsername(), summoner.getName());
    }

    @Test
    void create() {
        Role role = new Role();
        role.setRole(ERole.ADMINISTRATOR);
        roleRepository.save(role);
        User user = User.builder()
                .email("someemail@gmail.com")
                .username("someusername")
                .password("somepassword1!")
                .followedSummoners(null)
                .roles(Set.of(role))
                .build();
        assertNotNull(service.create(user));
    }

    @Test
    void sendEmail() throws MessagingException, IOException {
        ChampionDTO championDTO = championRepository.findByName("Zed").map(championMapper::toDto).orElseThrow();
        service.sendMail(championDTO);
    }
}