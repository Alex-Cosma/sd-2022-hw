package com.travel.BookingHotel;

import com.travel.BookingFlight.BookingFlightService;
import com.travel.BookingFlight.model.BookingFlight;
import com.travel.BookingFlight.model.dto.BookingFlightDTO;
import com.travel.BookingHotel.model.BookingHotel;
import com.travel.BookingHotel.model.dto.BookingHotelDTO;
import com.travel.TestCreationFactory;
import com.travel.flight.model.Flight;
import com.travel.hotel.HotelRepository;
import com.travel.hotel.model.Hotel;
import com.travel.user.UserRepository;
import com.travel.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static com.travel.TestCreationFactory.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class BookingHotelSeviceTest {
    @InjectMocks
    BookingHotelSevice bookingHotelSevice;

    @Mock
    BookingHotelMapper bookingHotelMapper;

    @Mock
    BookingHotelRepository bookingHotelRepository;

    @Mock
    HotelRepository hotelRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingHotelSevice = new BookingHotelSevice(bookingHotelRepository,bookingHotelMapper,hotelRepository,userRepository);
    }
    @Test
    void findAll() {
        List<BookingHotel> bookings = TestCreationFactory.listOf(BookingHotel.class);
        when(bookingHotelRepository.findAll()).thenReturn(bookings);

        List<BookingHotelDTO> all = bookingHotelSevice.findAll();

        Assertions.assertEquals(bookings.size(), all.size());
    }

    @Test
    void bookRoom() {
        BookingHotel bookingHotel = newBookingHotel();
        BookingHotelDTO bookingHotelDTO = newBookingHotelDTO();

        Hotel hotel = newHotel();
        String hotelName = randomString();
        hotel.setName(hotelName);

        User user = newUser();
        String username = randomString();
        user.setUsername(username);

        bookingHotel.setHotel(hotel);
        bookingHotel.setUsers(Set.of(user));
        bookingHotelDTO.setHotelName(hotelName);
        bookingHotelDTO.setUserNames(Set.of(username));
        when(bookingHotelMapper.fromDto(bookingHotelDTO)).thenReturn(bookingHotel);
        when(bookingHotelMapper.toDto(bookingHotel)).thenReturn(bookingHotelDTO);
        when(bookingHotelRepository.save(bookingHotel)).thenReturn(bookingHotel);
        when(hotelRepository.findByName(hotelName)).thenReturn(Optional.of(hotel));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        BookingHotelDTO newBookingDTO = bookingHotelSevice.bookRoom(bookingHotelDTO);
        assertEquals(newBookingDTO,bookingHotelDTO);
    }

    @Test
    void delete() {
        BookingHotel bookingHotel = newBookingHotel();
        when(bookingHotelRepository.save(bookingHotel)).thenReturn(bookingHotel);
        Hotel hotel = newHotel();
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        User user = newUser();
        when(userRepository.save(user)).thenReturn(user);
        bookingHotel.setHotel(hotel);
        bookingHotel.setUsers(Set.of(user));
        Long id = bookingHotel.getId();
        when(bookingHotelRepository.findById(id)).thenReturn(Optional.of(bookingHotel));
        doNothing().when(bookingHotelRepository).delete(bookingHotel);
        bookingHotelSevice.delete(bookingHotel.getId());
        assertFalse(bookingHotelRepository.existsById(id));
    }
}