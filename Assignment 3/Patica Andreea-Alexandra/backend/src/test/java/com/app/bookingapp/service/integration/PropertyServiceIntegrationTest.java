package com.app.bookingapp.service.integration;

import com.app.bookingapp.data.dto.mapper.PropertyMapper;
import com.app.bookingapp.data.dto.mapper.PropertyMapperImpl;
import com.app.bookingapp.data.dto.model.PropertyDto;
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
import com.app.bookingapp.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.app.bookingapp.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PropertyServiceIntegrationTest {
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;

    private PropertyMapper propertyMapper;

    private User user;
    private Role role;
    private AccountType accountType;

    @BeforeEach
    void setUp(){
        propertyMapper = new PropertyMapperImpl();

        propertyRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        accountTypeRepository.deleteAll();

        role = saveRole();
        accountType = saveAccountType();
        user = saveUser();
    }

    @Test
    void testFindAll() {

        int noProperties = 10;
        List<Property> properties = new ArrayList<>();

        for (int i = 0; i < noProperties; i++) {
            properties.add(buildProperty());
        }
        propertyRepository.saveAll(properties);

        List<PropertyDto> all = propertyService.findAll();

        assertEquals(noProperties, all.size());
    }

    @Test
    void testAllPropertiesByOwner(){
        int noProperties = 10;
        List<Property> properties = new ArrayList<>();

        for (int i = 0; i < noProperties; i++) {
            properties.add(buildProperty());
        }
        propertyRepository.saveAll(properties);

        List<PropertyDto> foundProperties = propertyService.allPropertiesByOwner(user.getUsername());
        assertEquals(noProperties, foundProperties.size());
    }

    @Test
    void testCreate(){
        Property property = buildProperty();

        PropertyDto propertyDto = propertyMapper.propertyToPropertyDto(property);

        PropertyDto savedPropertyDto = propertyService.create(propertyDto);

        assertEquals(savedPropertyDto, propertyDto);
    }

    @Test
    void testUpdateProperty(){
        Property property = buildProperty();

        PropertyDto propertyDto = propertyMapper.propertyToPropertyDto(property);

        PropertyDto savedPropertyDto = propertyService.update(1L, propertyDto);

        assertEquals(savedPropertyDto, propertyDto);
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

    private Property buildProperty(){
        return Property.builder()
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
                .build();
    }
}
