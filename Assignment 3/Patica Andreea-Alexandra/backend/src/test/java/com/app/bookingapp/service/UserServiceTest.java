package com.app.bookingapp.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.bookingapp.TestCreationFactory.randomLong;
import static com.app.bookingapp.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountTypeRepository accountTypeRepository;
    @Mock
    private RoleRepository roleRepository;

    private UserMapper userMapper;

    private Role role;
    private AccountType accountType;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, accountTypeRepository, roleRepository);

        role = buildRole();
        accountType = buildAccountType();
    }

    @Test
    void testFindAll() {
        List<User> users = new ArrayList<>();
        int noUsers = 10;
        for (int i = 0; i < noUsers; i++) {
            users.add(buildUser());
        }

        when(userRepository.findAll()).thenReturn(users);

        List<User> all = userService.findAll();

        assertEquals(noUsers, all.size());
    }

    @Test
    void testAddUSer(){
        User user = buildUser();
        UserDto userDto = userMapper.userToUserDto(user);

        when(roleRepository.findByName(any())).thenReturn(Optional.of(user.getRole()));
        when(accountTypeRepository.findByName(any())).thenReturn(Optional.of(user.getAccountType()));
        when(userRepository.save(any())).thenReturn(user);

        UserDto savedUserDto = userService.create(userDto);
        assertEquals(userDto, savedUserDto);
    }

    @Test
    void testUpdateProperty(){
        User user = buildUser();
        UserDto userDto = userMapper.userToUserDto(user);

        when(roleRepository.findByName(any())).thenReturn(Optional.of(user.getRole()));
        when(accountTypeRepository.findByName(any())).thenReturn(Optional.of(user.getAccountType()));
        when(userRepository.save(any())).thenReturn(user);

        UserDto savedUserDto = userService.update(randomLong(), userDto);
        assertEquals(userDto, savedUserDto);
    }

    @Test
    void delete(){
        Long id = randomLong();
        User user = buildUser();

        userRepository.save(user);

        doNothing().when(userRepository).deleteByUsername(user.getUsername());

        userService.delete(user.getUsername());

        verify(userRepository, times(1)).deleteByUsername(user.getUsername());
    }

    private User buildUser(){

        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        return User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .password(password)
                .role(role)
                .accountType(accountType)
                .phoneNumber(randomString().substring(0, 10))
                .noSqlId(randomLong())
                .build();
    }

    private Role buildRole(){
        return Role.builder()
                .name(ERole.CLIENT)
                .description(randomString())
                .build();
    }

    private AccountType buildAccountType(){
        return AccountType.builder()
                .name(EAccountType.BASIC)
                .description(randomString())
                .build();
    }
}
