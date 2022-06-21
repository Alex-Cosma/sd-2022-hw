package com.project.clinic.user;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.security.AuthService;
import com.project.clinic.skin_color.SkinColorRepository;
import com.project.clinic.skin_color.SkinColorService;
import com.project.clinic.user.dto.UserListDTO;
import com.project.clinic.user.dto.UserRegisterDTO;
import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SkinColorService skinColorService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, skinColorService, userMapper, authService);
    }

    @Test
    void allDermatologistsForList(){
        List<User> users = TestCreationFactory.listOf(User.class);
        for(User u: users){
            UserListDTO dto = UserListDTO.builder()
                    .id(u.getId())
                    .name(u.getUsername())
                    .treatments(u.getTreatments())
                    .points(u.getPoints())
                    .password(u.getPassword())
                    .roles(Collections.singleton("PROFESSIONAL"))
                    .skinColor(u.getSkinColor().getName().toString())
                    .build();
            when(userMapper.userListDtoFromUser(u)).thenReturn(dto);
        }

        when(userRepository.findAll()).thenReturn(users);

        List<UserListDTO> all = userService.allDermatologistsForList();

        Assertions.assertEquals(users.size(), all.size());
    }

    @Test
    void findById(){
        User user = TestCreationFactory.newUser();

        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        User found = userService.findById(user.getId());

        Assertions.assertEquals(user.getUsername(), found.getUsername());
    }

    @Test
    void findDTOById(){
        User u = TestCreationFactory.newUser();
        UserListDTO dto = UserListDTO.builder()
                .id(u.getId())
                .name(u.getUsername())
                .treatments(u.getTreatments())
                .points(u.getPoints())
                .password(u.getPassword())
                .roles(Collections.singleton("PROFESSIONAL"))
                .skinColor(u.getSkinColor().getName().toString())
                .build();

        when(userRepository.findById(u.getId())).thenReturn(java.util.Optional.of(u));
        when(userMapper.userListDtoFromUser(u)).thenReturn(dto);

        User found = userService.findById(u.getId());

        Assertions.assertEquals(dto.getName(), found.getUsername());
    }

    @Test
    void findAll(){
        List<User> users = TestCreationFactory.listOf(User.class);
        for(int i = 0; i < users.size(); i++){
            User u = users.get(i);
            UserListDTO dto = UserListDTO.builder()
                    .id(u.getId())
                    .name(u.getUsername())
                    .treatments(u.getTreatments())
                    .points(u.getPoints())
                    .password(u.getPassword())
                    .skinColor(u.getSkinColor().getName().toString())
                    .build();
            if(i % 2 == 0)
                dto.setRoles(Collections.singleton("CUSTOMER"));
            else
                dto.setRoles(Collections.singleton("PROFESSIONAL"));

            when(userMapper.userListDtoFromUser(u)).thenReturn(dto);
        }

        when(userRepository.findAll()).thenReturn(users);

        List<UserListDTO> all = userService.findAll();

        Assertions.assertEquals(users.size(), all.size());
    }

    @Test
    void create(){
        UserRegisterDTO userRegisterDTO = TestCreationFactory.newUserRegisterDTO();

        try {
            userService.create(userRegisterDTO);
        }
        catch (Exception e){
            fail("Should not have thrown exception");
        }
    }

    @Test
    void edit(){
        User u = TestCreationFactory.newUser();
        UserRegisterDTO dto = UserRegisterDTO.builder()
                .id(u.getId())
                .name(u.getUsername())
                .treatments(u.getTreatments())
                .points(u.getPoints())
                .password(u.getPassword())
                .roles(Collections.singleton("PROFESSIONAL"))
                .skinColor(u.getSkinColor().getName().toString())
                .build();

        String newName = TestCreationFactory.randomString();
        User changed = u;
        changed.setUsername(newName);

        when(userRepository.save(u)).thenReturn(changed);
        when(userMapper.userRegisterDtoFromUser(changed)).thenReturn(dto);
        when(userRepository.findById(u.getId())).thenReturn(java.util.Optional.of(u));

        UserRegisterDTO updated = userService.edit(u.getId(), dto);
        Assertions.assertEquals(changed.getUsername(), updated.getName());
    }

    @Test
    void delete(){
        UserRegisterDTO userRegisterDTO = TestCreationFactory.newUserRegisterDTO();

        try {
            userService.delete(userRegisterDTO.getId());
        }
        catch (Exception e){
            fail("Should not have thrown exception");
        }
    }

    @Test
    void findAllSkinColors(){
        List<SkinColor> skinColors = new ArrayList<>();
        skinColors.add(TestCreationFactory.newSkinColor());

        when(skinColorService.findAll()).thenReturn(skinColors);

        List<SkinColor> all = userService.findAllSkinColors();

        Assertions.assertEquals(skinColors.size(), all.size());
    }

    @Test
    void buy(){
        UserRegisterDTO userRegisterDTO = TestCreationFactory.newUserRegisterDTO();
        int price = new Random().nextInt(100);

        try {
            userService.buy(userRegisterDTO.getId(), 1L, price);
        }
        catch (Exception e){
            fail("Should not have thrown exception");
        }
    }
}
