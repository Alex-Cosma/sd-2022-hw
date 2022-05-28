package com.travel.BookingHotel;

import com.travel.BookingHotel.model.BookingHotel;

import com.travel.hotel.HotelRepository;
import com.travel.hotel.model.Hotel;
import com.travel.user.UserRepository;
import com.travel.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookingHotelRepositoryTest {
    @Autowired
    private BookingHotelRepository bookingHotelRepository;

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
    void findByStartDateAndEndDateAndHotel() {
        Hotel hotel = hotelRepository.save(Hotel.builder().name("numeHotel").price(100).places(100).build());
        userRepository.save(User.builder().username("aaa").password("aeffdsfer").email("ameil").build());
        Date depDate = new Date();
        Date arrivDate = new Date();
        bookingHotelRepository.save(BookingHotel.builder().startDate(depDate).endDate(arrivDate).price(100L).places(100).build());
        assertNotNull(bookingHotelRepository.findByStartDateAndEndDateAndHotel(depDate,arrivDate,hotel));

    }
}