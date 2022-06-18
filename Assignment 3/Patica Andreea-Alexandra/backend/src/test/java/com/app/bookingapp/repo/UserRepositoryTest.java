package com.app.bookingapp.repo;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static com.app.bookingapp.TestCreationFactory.randomLong;
import static com.app.bookingapp.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    private Role role;
    private AccountType accountType;
    private User user;

    @BeforeEach
    void setUp(){
        roleRepository.deleteAll();
        accountTypeRepository.deleteAll();
        userRepository.deleteAll();

        role = saveRole();
        accountType = saveAccountType();
        user = saveUser();
    }

    @Test
    public void testCreate() {
        String email = "email@employee.com";
        String password = "Abcdefg1234!";

        assertNotNull(user);

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(User.builder()
                    .username(randomString())
                    .email(email)
                    .password(password)
                    .build());
        });
    }

    @Test
    public void findByUsername(){
        String email = "email@employee.com";
        String password = "Abcdefg1234!";

        Optional<User> result = userRepository.findByUsername(user.getUsername());
        assertTrue(result.isPresent());
        result.ifPresent(u -> assertEquals(u.getId(), user.getId()));
    }

    @Test
    public void existsByUsername(){
        String username = randomString();
        String email = "email@employee.com";
        String password = "Abcdefg1234!";

        Boolean result = userRepository.existsByUsername(user.getUsername());
        assertEquals(result, true);
    }

    @Test
    public void existsByEmail(){
        String username = randomString();
        String email = "email@employee.com";
        String password = "Abcdefg1234!";

        Boolean result = userRepository.existsByEmail(email);
        assertEquals(result, true);
    }

    @Test
    public void testDeleteByUsername(){
        userRepository.deleteByUsername(user.getUsername());

        Optional<User> result = userRepository.findById(user.getId());
        assertTrue(result.isEmpty());
    }


    private User saveUser(){

        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        return userRepository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .password(password)
                .role(role)
                .accountType(accountType)
                .phoneNumber(randomString().substring(0, 10))
                .noSqlId(randomLong())
                .build());
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
}
