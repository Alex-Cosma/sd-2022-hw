package com.travel.BookingHotel;

import com.travel.BookingHotel.model.BookingHotel;
import com.travel.BookingHotel.model.dto.BookingHotelDTO;
import com.travel.hotel.HotelMapper;
import com.travel.hotel.HotelRepository;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import com.travel.user.UserRepository;
import com.travel.user.dto.UserDTO;
import com.travel.user.mapper.UserMapper;
import com.travel.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.travel.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
@SpringBootTest
public class BookingHotelServiceIntegrationtest {
    @Autowired
    private BookingHotelRepository bookingHotelRepository;

    @Autowired
    private BookingHotelSevice bookingHotelSevice;

    @Autowired
    private BookingHotelMapper bookingHotelMapper;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        hotelRepository.deleteAll();
        bookingHotelRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    void findAll(){
        Hotel hotel = Hotel.builder().places(100).id(1L).name("hotel").price(100).build();
        hotelRepository.save(hotel);
        User user = User.builder().id(1l).username("username").password("password").email(randomEmail()).build();
        userRepository.save(user);
        List<BookingHotel> bookings = listOf(BookingHotel.class);
        bookings.forEach(bookingHotel -> bookingHotel.setHotel(hotel));
        bookings.forEach(bookingHotel -> bookingHotel.setUsers(Set.of(user)));
        bookings.forEach(bookingHotel -> bookingHotel.setPlaces(100));
        bookings.forEach(bookingHotel -> bookingHotel.setPrice(100L));
        bookingHotelRepository.saveAll(bookings);
        List<BookingHotelDTO> all = bookingHotelSevice.findAll();
        assertEquals(all.size(),bookings.size());
    }
//    @Test
//    void delete(){
//        HotelDTO hotelDTO = newHotelDTO();
//        hotelRepository.save(hotelMapper.fromDto(hotelDTO));
//        UserDTO userDTO = newUserDTO();
//        userRepository.save(userMapper.fromDto(userDTO));
//        BookingHotelDTO bookingHotelDTO = newBookingHotelDTO();
//        bookingHotelDTO.setHotelName(hotelDTO.getName());
//        BookingHotelDTO bookingHotelDTO1 = bookingHotelSevice.bookRoom(bookingHotelDTO);
//        bookingHotelSevice.delete(bookingHotelDTO1.getId());
//        assertFalse(bookingHotelRepository.existsById(bookingHotelDTO1.getId()));
//    }
}
