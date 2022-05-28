package com.travel.hotel;

import com.travel.city.CityRepository;
import com.travel.city.model.City;
import com.travel.hotel.model.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.travel.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
public class HotelRepositoryTest {
    @Autowired
    private HotelRepository repository;
    @Autowired
    private CityRepository cityRepository;
    @BeforeEach
    public void setUp(){
        cityRepository.deleteAll();
        repository.deleteAll();
    }


    @Test
    public void testFindByName(){
        City city = cityRepository.save(City.builder().id(1L).name("aa").build());

        Hotel hotel1 = repository.save(Hotel.builder().name("name1")
                .price(randomBoundedInt(50))
                .city(city)
                .places(randomBoundedInt(250))
                .build());

        assertNotNull(repository.findByName("name1"));
    }
}
