package com.app.bookingapp.service;

import com.app.bookingapp.data.dto.mapper.PropertyMapper;
import com.app.bookingapp.data.dto.mapper.PropertyMapperImpl;
import com.app.bookingapp.data.dto.model.PropertyDto;
import com.app.bookingapp.data.sql.entity.AccountType;
import com.app.bookingapp.data.sql.entity.Property;
import com.app.bookingapp.data.sql.entity.Role;
import com.app.bookingapp.data.sql.entity.User;
import com.app.bookingapp.data.sql.entity.enums.EAccountType;
import com.app.bookingapp.data.sql.entity.enums.ERole;
import com.app.bookingapp.data.sql.repo.PropertyRepository;
import com.app.bookingapp.data.sql.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.bookingapp.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PropertyServiceTest {
    @InjectMocks
    private PropertyService propertyService;

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private UserRepository userRepository;

    private PropertyMapper propertyMapper;

    private User user;
    private Role role;
    private AccountType accountType;

    @BeforeEach
    void setUp() {
        propertyMapper = new PropertyMapperImpl();
        MockitoAnnotations.openMocks(this);
        propertyService = new PropertyService(propertyRepository, propertyMapper, userRepository);

        role = buildRole();
        accountType = buildAccountType();
        user = buildUser();
    }

    @Test
    void testFindAll() {
        List<Property> properties = new ArrayList<>();
        int noProperties = 10;
        for (int i = 0; i < noProperties; i++) {
            properties.add(buildProperty());
        }

        when(propertyRepository.findAll()).thenReturn(properties);

        List<PropertyDto> all = propertyService.findAll();

        assertEquals(noProperties, all.size());
    }

    @Test
    void testAllPropertiesByUser(){
        Long id = randomLong();
        String username = randomString();
        User user = User.builder()
                .id(id)
                .username(username)
                .build();

        List<Property> properties = new ArrayList<>();
        int noProperties = 10;
        for (int i = 0; i < noProperties; i++) {
            properties.add(buildProperty());
        }

        when(propertyRepository.findAllByOwnerUsername(user.getUsername())).thenReturn(properties);

        List<PropertyDto> foundProperties = propertyService.allPropertiesByOwner(user.getUsername());
        Assertions.assertEquals(noProperties, foundProperties.size());
    }

    @Test
    void testAddProperty(){
        Property property = buildProperty();
        PropertyDto propertyDto = propertyMapper.propertyToPropertyDto(property);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(propertyRepository.save(any())).thenReturn(property);

        PropertyDto savedPropertyDto = propertyService.create(propertyDto);
        assertEquals(propertyDto, savedPropertyDto);
    }

    @Test
    void testUpdateProperty(){
        Property property = buildProperty();
        PropertyDto propertyDto = propertyMapper.propertyToPropertyDto(property);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(propertyRepository.save(any())).thenReturn(property);

        PropertyDto savedPropertyDto = propertyService.update(randomLong(), propertyDto);
        assertEquals(propertyDto, savedPropertyDto);
    }

    @Test
    void delete(){
        Long id = randomLong();
        Property property = buildProperty();
        property.setId(id);

        propertyRepository.save(property);

        doNothing().when(propertyRepository).deleteById(id);

        propertyService.delete(id);

        verify(propertyRepository, times(1)).deleteById(id);
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
