package com.example.airbnb.booking;

import com.example.airbnb.accommodation.AccommodationRepository;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.booking.model.dto.BookingDTO;
import com.example.airbnb.security.AuthService;
import com.example.airbnb.security.dto.SignupRequest;
import com.example.airbnb.user.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        bookingRepository.deleteAll();
    }

    @Test
    void save() throws ParseException {
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

        String date_string = "2023-02-01";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date = formatter.parse(date_string);

        String date_string1 = "2023-03-01";
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date end_date = formatter.parse(date_string);

        BookingDTO bookingDTO = BookingDTO.builder().accommodation_id(accommodation1.getId())
                .guest_id(user.getId()).start_date(start_date).end_date(end_date).build();

        BookingDTO bookingDTO1 = bookingService.save(bookingDTO);

        assertEquals(bookingDTO1.getGuest_id(), bookingDTO.getGuest_id());
        assertEquals(bookingDTO1.getAccommodation_id(), bookingDTO.getAccommodation_id());
    }

}