package com.project.clinic.user;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.appointment.AppointmentRepository;
import com.project.clinic.security.AuthService;
import com.project.clinic.skin_color.SkinColorRepository;
import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.user.dto.UserListDTO;
import com.project.clinic.user.dto.UserRegisterDTO;
import com.project.clinic.user.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
public class UserServiceIntegrationTest {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkinColorRepository skinColorRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    private SkinColor skinColor;
    private Role proffesional;
    private Role customer;

    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        skinColorRepository.deleteAll();

        skinColor = TestCreationFactory.newSkinColor();
        skinColorRepository.save(skinColor);

        proffesional = TestCreationFactory.newRole();
        proffesional.setName(ERole.valueOf("PROFESSIONAL"));
        roleRepository.save(proffesional);

        customer = TestCreationFactory.newRole();
        customer.setName(ERole.valueOf("CUSTOMER"));
        roleRepository.save(customer);
    }

    @Test
    void allDermatologistsForList(){
        List<UserRegisterDTO> users = TestCreationFactory.listOf(UserRegisterDTO.class);
        users.forEach(x -> x.setRoles(Collections.singleton(proffesional.getName().toString())));
        users.forEach(x -> x.setSkinColor(skinColor.getName().toString()));
        users.forEach(authService::createNewUserFromDTO);

        List<UserListDTO> all = userService.allDermatologistsForList();

        Assertions.assertEquals(users.size(), all.size());
    }

    @Test
    void findById(){
        UserRegisterDTO user = TestCreationFactory.newUserRegisterDTO();
        authService.createNewUserFromDTO(user);

        Optional<User> saved = userRepository.findByUsername(user.getName());
        Assertions.assertTrue(saved.isPresent());

        User found = userService.findById(saved.get().getId());
        Assertions.assertEquals(user.getName(), found.getUsername());
    }

    @Test
    void findDTOById(){
        UserRegisterDTO user = TestCreationFactory.newUserRegisterDTO();
        authService.createNewUserFromDTO(user);

        Optional<User> saved = userRepository.findByUsername(user.getName());
        Assertions.assertTrue(saved.isPresent());

        UserListDTO found = userService.findDTOById(saved.get().getId());
        Assertions.assertEquals(user.getName(), found.getName());
    }

    @Test
    void findAll(){
        List<UserRegisterDTO> users = TestCreationFactory.listOf(UserRegisterDTO.class);
        users.forEach(x -> x.setSkinColor(skinColor.getName().toString()));
        users.forEach(authService::createNewUserFromDTO);

        List<UserListDTO> all = userService.findAll();

        Assertions.assertEquals(users.size(), all.size());
    }

    @Test
    void create(){
        UserRegisterDTO user = TestCreationFactory.newUserRegisterDTO();
        userService.create(user);

        Optional<User> saved = userRepository.findByUsername(user.getName());
        Assertions.assertTrue(saved.isPresent());
    }

    @Test
    void edit(){
        UserRegisterDTO user = TestCreationFactory.newUserRegisterDTO();
        authService.createNewUserFromDTO(user);

        Optional<User> saved = userRepository.findByUsername(user.getName());
        Assertions.assertTrue(saved.isPresent());

        User found = saved.get();
        String newName = TestCreationFactory.randomString();
        user.setId(found.getId());
        user.setName(newName);
        userService.edit(found.getId(), user);

        Optional<User> foundUpdated = userRepository.findById(found.getId());
        Assertions.assertTrue(foundUpdated.isPresent());

        Assertions.assertEquals(newName, foundUpdated.get().getUsername());
    }

    @Test
    void delete(){
        UserRegisterDTO user = TestCreationFactory.newUserRegisterDTO();
        authService.createNewUserFromDTO(user);

        Optional<User> saved = userRepository.findByUsername(user.getName());
        Assertions.assertTrue(saved.isPresent());

        Assertions.assertEquals(1, userRepository.findAll().size());

        User found = saved.get();
        userService.delete(found.getId());

        Assertions.assertEquals(0, userRepository.findAll().size());
    }

    @Test
    void findAllSkinColors(){
        List<SkinColor> skinColors = userService.findAllSkinColors();

        Assertions.assertEquals(1, skinColors.size());
    }

    @Test
    void buy(){
        UserRegisterDTO user = TestCreationFactory.newUserRegisterDTO();
        authService.createNewUserFromDTO(user);

        Assertions.assertEquals(0, user.getPoints());

        Optional<User> saved = userRepository.findByUsername(user.getName());
        Assertions.assertTrue(saved.isPresent());

        User found = saved.get();
        int price = new Random().nextInt(100);
        userService.buy(found.getId(), 1L, price);

        saved = userRepository.findByUsername(user.getName());
        Assertions.assertEquals(price, saved.get().getPoints());
    }
}
