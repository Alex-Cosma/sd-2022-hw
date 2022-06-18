package com.app.bookingapp.repo;

import com.app.bookingapp.data.sql.entity.AccountType;
import com.app.bookingapp.data.sql.entity.Property;
import com.app.bookingapp.data.sql.entity.Role;
import com.app.bookingapp.data.sql.entity.User;
import com.app.bookingapp.data.sql.entity.enums.EAccountType;
import com.app.bookingapp.data.sql.entity.enums.ERole;
import com.app.bookingapp.data.sql.repo.AccountTypeRepository;
import com.app.bookingapp.data.sql.repo.PropertyRepository;
import com.app.bookingapp.data.sql.repo.RoleRepository;
import com.app.bookingapp.data.sql.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.bookingapp.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PropertyRepositoryTest {

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @BeforeEach
    void setUp(){
        propertyRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        accountTypeRepository.deleteAll();
    }

    @Test
    public void testCreate() {
        Property savedProperty = saveProperty();

        assertNotNull(savedProperty);

        assertThrows(DataIntegrityViolationException.class, () -> {
            propertyRepository.save(Property.builder()
                    .build());
        });
    }

    @Test
    public void testFindAllByOwnerUsername(){
        User user = saveUser();

        int noProperties = 10;
        List<Property> properties = new ArrayList<>();

        for (int i = 0; i < noProperties; i++) {
            properties.add(saveProperty(user));
        }

        List<Property> all = propertyRepository.findAllByOwnerUsername(user.getUsername());
        assertEquals(properties.size(), all.size());
    }

    @Test
    public void testFindPropertyByName(){
        Property savedProperty = saveProperty();

        Optional<Property> result = propertyRepository.findPropertyByName(savedProperty.getName());

        assertTrue(result.isPresent());
        result.ifPresent(p -> assertEquals(p.getId(), savedProperty.getId()));
    }

    @Test
    public void testDeleteById(){
        Property propertySaved = saveProperty();

        propertyRepository.deleteById(propertySaved.getId());

        Optional<Property> result = propertyRepository.findById(propertySaved.getId());
        assertTrue(result.isEmpty());
    }


    private User saveUser(){
        Role role = saveRole();
        AccountType accountType = saveAccountType();

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

    private Property saveProperty(User user){
        return propertyRepository.save(Property.builder()
                .name(randomString())
                .address(randomString())
                .description(randomString())
                .owner(user)
                .kitchen(randomLong())
                .numberOfBathrooms(randomLong())
                .numberOfBeds(randomLong())
                .numberOfRooms(randomLong())
                .picture(randomBytes())
                .price(randomFloat())
                .rating(randomFloat())
                .description(randomString())
                .picturesId(randomLong())
                .build());
    }

    private Property saveProperty(){
        return propertyRepository.save(Property.builder()
                .name(randomString())
                .address(randomString())
                .description(randomString())
                .owner(saveUser())
                .kitchen(randomLong())
                .numberOfBathrooms(randomLong())
                .numberOfBeds(randomLong())
                .numberOfRooms(randomLong())
                .picture(randomBytes())
                .price(randomFloat())
                .rating(randomFloat())
                .description(randomString())
                .picturesId(randomLong())
                .build());
    }
}
