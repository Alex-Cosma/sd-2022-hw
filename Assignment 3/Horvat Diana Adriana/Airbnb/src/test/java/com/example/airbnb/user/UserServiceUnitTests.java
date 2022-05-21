package com.example.airbnb.user;

import com.example.airbnb.TestCreationFactory;
import com.example.airbnb.accommodation.AccommodationService;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.user.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceUnitTests {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, roleRepository);
    }

    @Test
    void findById(){
        User user = TestCreationFactory.user();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User foundUser = userService.findById(user.getId());

        assertEquals(user, foundUser);
    }

    @Test
    void getUserAccommodations(){
        Accommodation accommodation = TestCreationFactory.accommodation();
        User user = TestCreationFactory.user();
        user.setAccommodations(Set.of(accommodation));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Set<Accommodation> accommodations = userService.getUserAccommodations(user.getId());

        assertEquals(1, accommodations.size());
        assertTrue(accommodations.contains(accommodation));
    }

    @Test
    void getUsersBookings() throws ParseException {
        Accommodation accommodation = TestCreationFactory.accommodation();
        User user = TestCreationFactory.user();

        Booking booking = TestCreationFactory.newBooking();
        booking.setAccommodation(accommodation);
        booking.setGuest(user);

        user.setBookings(Set.of(booking));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Set<Booking> bookings = userService.getUsersBookings(user.getId());

        assertEquals(1, bookings.size());
        assertTrue(bookings.contains(booking));

    }

    @Test
    void addUserHostRole(){
        User user = TestCreationFactory.user();
        Role roleGuest = TestCreationFactory.role(ERole.GUEST);
        Role roleHost = TestCreationFactory.role(ERole.HOST);
        Set<Role> roles = Set.of(roleGuest);
        user.setRoles(roles);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(roleRepository.findByName(ERole.HOST)).thenReturn(Optional.of(roleHost));
        when(userRepository.save(user)).thenReturn(user);

        userService.addUserHostRole(user.getId());
    }

}