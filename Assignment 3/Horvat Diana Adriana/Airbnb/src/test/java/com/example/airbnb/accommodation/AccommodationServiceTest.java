package com.example.airbnb.accommodation;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import com.example.airbnb.address.model.Address;
import com.example.airbnb.amenities.model.Amenity;
import com.example.airbnb.image.model.ImageURL;
import com.example.airbnb.review.model.Review;
import com.example.airbnb.security.AuthService;
import com.example.airbnb.security.dto.SignupRequest;
import com.example.airbnb.user.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AccommodationServiceTest {
    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        accommodationRepository.deleteAll();
    }

    @Test
    void findAll(){
          assertEquals(0, accommodationService.findAll().getTotalElements());

        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }

        authService.register(SignupRequest.builder()
                .email("alex1@email.com")
                .username("alex1")
                .password("WooHoo1!")
                .roles(Set.of("GUEST"))
                .build());

        User user = userRepository.findByUsername("alex1").orElse(null);


        final Accommodation accommodation = Accommodation.builder().
                name("Accommodation").description("Description")
                .house_rules("House Rules").property_type("Property Type").room_type("Room Type").bathrooms(2)
                .bedrooms(2).beds(2).price(100.1).user(user).build();

      accommodationService.save(accommodation);

      assertEquals(1, accommodationService.findAll().getTotalElements());

    }

    @Test
    void findById(){
        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }

        authService.register(SignupRequest.builder()
                .email("alex1@email.com")
                .username("alex1")
                .password("WooHoo1!")
                .roles(Set.of("GUEST"))
                .build());

        User user = userRepository.findByUsername("alex1").orElse(null);

        final Accommodation accommodation = Accommodation.builder().
                name("Accommodation").description("Description")
                .house_rules("House Rules").property_type("Property Type").room_type("Room Type").bathrooms(2)
                .bedrooms(2).beds(2).price(100.1).user(user).build();

        Accommodation accommodation1 = accommodationRepository.save(accommodation);

        Accommodation accommodation2 = accommodationService.findById(accommodation1.getId());

        assertEquals(accommodation1.getId(), accommodation2.getId());
    }

    @Test
    void delete(){
        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }

        authService.register(SignupRequest.builder()
                .email("alex1@email.com")
                .username("alex1")
                .password("WooHoo1!")
                .roles(Set.of("GUEST"))
                .build());

        User user = userRepository.findByUsername("alex1").orElse(null);

        final Accommodation accommodation = Accommodation.builder().
                name("Accommodation").description("Description")
                .house_rules("House Rules").property_type("Property Type").room_type("Room Type").bathrooms(2)
                .bedrooms(2).beds(2).price(100.1).user(user).build();

        Accommodation accommodation1 = accommodationRepository.save(accommodation);

        assertEquals(1, accommodationService.findAll().getTotalElements());

        accommodationService.delete(accommodation1.getId());

        assertEquals(0, accommodationService.findAll().getTotalElements());
    }

    @Test
    void create(){
        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }

        authService.register(SignupRequest.builder()
                .email("alex1@email.com")
                .username("alex1")
                .password("WooHoo1!")
                .roles(Set.of("GUEST"))
                .build());

        User user = userRepository.findByUsername("alex1").orElse(null);

        AccommodationDTO accommodationDTO = AccommodationDTO.builder().name("Accommodation").description("Description")
                .house_rules("House Rules").property_type("Property Type").room_type("Room Type").bathrooms("2").bedrooms("2")
                .beds("2").price(100.1).imageURL("imageURL").address_city("address_city").address_city("address_country")
                .amenities("amenity1, amenity2, amenity3, amenity4")
                .address_street("address_street").user_id(user.getId()).build();

        AccommodationDTO accommodationDTO1 = accommodationService.create(accommodationDTO);

        assertEquals(accommodationDTO.getName(), accommodationDTO1.getName());

    }

    @Test
    void edit(){
        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }

        authService.register(SignupRequest.builder()
                .email("alex1@email.com")
                .username("alex1")
                .password("WooHoo1!")
                .roles(Set.of("GUEST"))
                .build());

        User user = userRepository.findByUsername("alex1").orElse(null);

        AccommodationDTO accommodationDTO = AccommodationDTO.builder().name("Accommodation").description("Description")
                .house_rules("House Rules").property_type("Property Type").room_type("Room Type").bathrooms("2").bedrooms("2")
                .beds("2").price(100.1).imageURL("imageURL").address_city("address_city").address_city("address_country")
                .amenities("amenity1, amenity2, amenity3, amenity4")
                .address_street("address_street").user_id(user.getId()).build();

        AccommodationDTO accommodationDTO1 = accommodationService.create(accommodationDTO);

        accommodationDTO1.setName("Accommodation1");

        AccommodationDTO accommodationDTO2 = accommodationService.edit(accommodationDTO1.getId(), accommodationDTO1);

        assertEquals(accommodationDTO1.getName(), accommodationDTO2.getName());

    }
}