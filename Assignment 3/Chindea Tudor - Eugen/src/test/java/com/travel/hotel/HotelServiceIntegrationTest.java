package com.travel.hotel;

import com.travel.TestCreationFactory;
import com.travel.city.CityMapper;
import com.travel.city.CityRepository;
import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import com.travel.flight.model.Flight;
import com.travel.flight.model.dto.FlightDTO;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.travel.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class HotelServiceIntegrationTest {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private HotelMapper hotelMapper;

    @BeforeEach
    void setUp(){
        hotelRepository.deleteAll();
        cityRepository.deleteAll();
    }
    //this test works when you run it alone
    @Test
    void findAll(){
        City city = City.builder().id(1L).name("aa").build();
        cityRepository.save(city);
        List<Hotel> hotels = listOf(Hotel.class);
        hotels.forEach(hotel -> hotel.setCity(city));
        hotels.forEach(hotel -> hotel.setName("Name"));
        hotels.forEach(hotel -> hotel.setPlaces(100));
        hotels.forEach(hotel -> hotel.setPrice(100));

        hotelRepository.saveAll(hotels);

        List<HotelDTO> all = hotelService.findAll();

        assertEquals(all.size(),hotels.size());
    }
    @Test
    void create(){
        City city = City.builder().id(1L).name("aa").build();
        cityRepository.save(city);
        HotelDTO hotelDTO = newHotelDTO();
        hotelDTO.setName("nume");
        hotelDTO.setCityName("aa");
        HotelDTO newHotel = hotelService.create(hotelDTO);
        assertEquals(newHotel.getName(),hotelDTO.getName());
    }

    @Test
    void delete() {
        CityDTO cityDTO = newCityDTO();
        City city = cityRepository.save(cityMapper.fromDto(cityDTO));

        HotelDTO hotelDTO = newHotelDTO();
        hotelDTO.setCityName(cityDTO.getName());
        HotelDTO newHotel = hotelService.create(hotelDTO);
        hotelService.delete(newHotel.getId());
        assertFalse(hotelRepository.existsById(newHotel.getId()));
    }
    @Test
    void edit(){
        CityDTO cityDTO = newCityDTO();
        City city = cityRepository.save(cityMapper.fromDto(cityDTO));
        HotelDTO hotelDTO = newHotelDTO();
        hotelDTO.setCityName(city.getName());
        hotelDTO.setPlaces(100);
        hotelDTO.setPrice(50);
        hotelDTO.setName("hotel");
        Hotel hotel = hotelMapper.fromDto(hotelDTO);
        hotel.setCity(city);
        Hotel newHotel = hotelRepository.save(hotel);
        hotelDTO=hotelMapper.toDto(newHotel);
        hotelDTO.setCityName(newHotel.getCity().getName());
        assertEquals("hotel",hotelDTO.getName());
        hotelDTO.setName("NewHotel");
        HotelDTO editedHotel = hotelService.edit(hotelDTO.getId(),hotelDTO);
        assertEquals("NewHotel",editedHotel.getName());
    }
}
