package com.example.airbnb.user;

import com.example.airbnb.TestCreationFactory;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.review.ReviewController;
import com.example.airbnb.user.dto.UserListDTO;
import com.example.airbnb.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import static com.example.airbnb.user.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends com.example.bookstore.BaseControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getUserAccommodations() throws Exception {
        User user = TestCreationFactory.user();
        Accommodation accommodation = TestCreationFactory.accommodationWithUser(user);
        Set<Accommodation> accommodationSet = new HashSet<>();
        accommodationSet.add(accommodation);

        user.setAccommodations(accommodationSet);

        when(userService.getUserAccommodations(user.getId())).thenReturn(accommodationSet);

        ResultActions result = performGetWithPathVariable(USERS + USER_ACCOMMODATIONS, user.getId());

        verify(userService, times(1)).getUserAccommodations(user.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(accommodationSet));
    }

    @Test
    void getUserBookings() throws Exception {
        User user = TestCreationFactory.user();
        Booking booking = TestCreationFactory.newBooking();
        Set<Booking> bookingSet = new HashSet<>();
        bookingSet.add(booking);
        booking.setGuest(user);

        when(userService.getUsersBookings(user.getId())).thenReturn(bookingSet);
        ResultActions result = performGetWithPathVariable(USERS + USER_BOOKINGS, user.getId());

        verify(userService, times(1)).getUsersBookings(user.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookingSet));

    }

    @Test
    void addUserHostRole() throws Exception {
        User user = TestCreationFactory.user();
        UserListDTO userListDTO = new UserListDTO();

        doNothing().when(userService).addUserHostRole(user.getId());

        ResultActions result = performPatchWithRequestBodyAndPathVariable(USERS + ENTITY, userListDTO,user.getId());
        verify(userService, times(1)).addUserHostRole(user.getId());
        result.andExpect(status().isOk());
    }
}