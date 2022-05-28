package com.travel.city;

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
public class CityRepositoryTest {
    @Autowired
    private  CityRepository repository;

    @BeforeEach
    public void setUp(){
        repository.deleteAll();
    }
    @Test
    void findByName() {
        repository.save(City.builder().name("name1").build());
        assertNotNull(repository.findByName("name1"));

    }
}
