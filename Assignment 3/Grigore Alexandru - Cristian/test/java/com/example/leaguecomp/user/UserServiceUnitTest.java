package com.example.leaguecomp.user;

import com.example.leaguecomp.BaseControllerTest;
import com.example.leaguecomp.TestCreationFactory;
import com.example.leaguecomp.champion.ChampionRepository;
import com.example.leaguecomp.champion.ChampionService;
import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.mapper.ChampionMapper;
import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.ItemRepository;
import com.example.leaguecomp.item.model.Item;
import com.example.leaguecomp.mail.MailService;
import com.example.leaguecomp.rune.RuneRepository;
import com.example.leaguecomp.rune.model.Rune;
import com.example.leaguecomp.security.AuthService;
import com.example.leaguecomp.security.dto.SignupRequest;
import com.example.leaguecomp.summoner.RegionRepository;
import com.example.leaguecomp.summoner.SummonerRepository;
import com.example.leaguecomp.summoner.SummonerService;
import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import com.example.leaguecomp.summoner.model.Summoner;
import com.example.leaguecomp.user.dto.UserListDTO;
import com.example.leaguecomp.user.mapper.UserMapper;
import com.example.leaguecomp.user.model.ERole;
import com.example.leaguecomp.user.model.Role;
import com.example.leaguecomp.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.mail.MessagingException;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.*;

import static com.example.leaguecomp.TestCreationFactory.*;
import static com.example.leaguecomp.TestCreationFactory.randomString;
import static com.example.leaguecomp.user.model.ERole.ADMINISTRATOR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceUnitTest {
    @InjectMocks
    private UserService service;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RuneRepository runeRepository;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private SummonerRepository summonerRepository;

    @Mock
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChampionRepository championRepository;

    @Mock
    private ChampionMapper championMapper;
    @Mock
    private UserMapper mapper;

    @Mock
    private MailService mailService;

    @Mock
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UserService(userRepository,mapper,roleRepository,summonerRepository,mailService,championRepository,itemRepository,runeRepository);
    }

    @Test
    void findAllList(){
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.saveAll(users)).thenReturn(users);

        List<UserListDTO> userListDTOS = new ArrayList<>();
        userListDTOS = service.allUsersForList();
        when(service.allUsersForList()).thenReturn(userListDTOS);
        assertNotNull(userListDTOS);
    }

    @Test
    void edit(){
        long id = randomLong();
        User user = newUser();
        Role role = newRole();
        when(roleRepository.findByRole(ERole.valueOf("ADMINISTRATOR"))).thenReturn(Optional.ofNullable(role));
        when(userRepository.getById(id)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        String username = randomString();
        UserListDTO userListDTO = UserListDTO.builder()
                .name(username)
                .id(id)
                .email(randomEmail())
                .roles(Set.of("ADMINISTRATOR"))
                .summoners(Set.of(newSummoner()))
                .build();
        service.edit(id,userListDTO);
        when(service.edit(id,userListDTO)).thenReturn(userListDTO);
        assertEquals(userListDTO.getName(),username);
    }

    @Test
    void delete(){
        long id = randomLong();
        User user = newUser();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));
        service.delete(id);
        verify(userRepository,times(1)).delete(user);
    }

    @Test
    void create(){
        Role role = new Role();
        role.setRole(ERole.ADMINISTRATOR);
        User user = User.builder()
                .email("someemail@gmail.com")
                .username("someusername")
                .password("somepassword1!")
                .followedSummoners(null)
                .roles(Set.of(role))
                .build();
        service.create(user);
        when(service.create(user)).thenReturn(user);
        assertEquals(user.getUsername(),"someusername");
    }

    @Test
    void follow(){
        Summoner summoner = newSummoner();
        User user = newUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(summonerRepository.findByName(summoner.getName())).thenReturn(Optional.ofNullable(summoner));
        service.followSummoner(user.getUsername(),summoner.getName());
        verify(summonerRepository,times(1)).findByName(summoner.getName());
    }


}