package com.app.bookingapp.service.integration;

import com.app.bookingapp.data.dto.mapper.UserMapper;
import com.app.bookingapp.data.dto.mapper.UserMapperImpl;
import com.app.bookingapp.data.dto.model.UserDto;
import com.app.bookingapp.data.sql.entity.AccountType;
import com.app.bookingapp.data.sql.entity.Role;
import com.app.bookingapp.data.sql.entity.User;
import com.app.bookingapp.data.sql.entity.enums.EAccountType;
import com.app.bookingapp.data.sql.entity.enums.ERole;
import com.app.bookingapp.data.sql.repo.AccountTypeRepository;
import com.app.bookingapp.data.sql.repo.RoleRepository;
import com.app.bookingapp.data.sql.repo.UserRepository;
import com.app.bookingapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.app.bookingapp.TestCreationFactory.randomLong;
import static com.app.bookingapp.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;

    private UserMapper userMapper;

    private Role role;
    private AccountType accountType;

    @BeforeEach
    void setUp(){
        userMapper = new UserMapperImpl();

        userRepository.deleteAll();
        roleRepository.deleteAll();
        accountTypeRepository.deleteAll();

        role = saveRole();
        accountType = saveAccountType();
    }

    @Test
    void testFindAll() {

        int noUsers = 10;
        List<User> users = new ArrayList<>();

        for (int i = 0; i < noUsers; i++) {
            users.add(buildUser());
        }
        userRepository.saveAll(users);

        List<UserDto> all = userService.findAll();

        assertEquals(noUsers, all.size());
    }

    @Test
    void testCreate(){
        User user = (buildUser());

        UserDto userDto = userMapper.userToUserDto(user);

        UserDto savedUserDto = userService.create(userDto);

        assertEquals(savedUserDto, userDto);
    }

    @Test
    void testUpdateUser(){
        User user = (buildUser());

        UserDto userDto = userMapper.userToUserDto(user);

        UserDto savedUserDto = userService.update(1L, userDto);

        assertEquals(savedUserDto, userDto);
    }



    private Role saveRole(){
        return roleRepository.save(Role.builder()
                .name(ERole.CLIENT)
                .description(randomString())
                .build());
    }

    private AccountType saveAccountType(){
        return accountTypeRepository.save(AccountType.builder()
                .name(EAccountType.BASIC)
                .description(randomString())
                .build());
    }

    private User buildUser(){

        String password = "Abcdefg1234!";
        return User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(randomString() + "@employee.com")
                .password(password)
                .role(role)
                .accountType(accountType)
                .phoneNumber(randomString().substring(0, 10))
                .noSqlId(randomLong())
                .build();
    }
}
