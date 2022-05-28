package com.travel.city;
import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import static com.travel.TestCreationFactory.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
public class CityServiceIntegrationTest {
    @Autowired
    private CityService cityService;

    @Autowired
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        cityRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<City> cities = listOf(City.class);
        cityRepository.saveAll(cities);

        List<CityDTO> all = cityService.findAll();
        assertEquals(cities.size(), all.size());
    }

    @Test
    void create(){
        CityDTO cityDTO = newCityDTO();

        CityDTO savedCity = cityService.create(cityDTO);

        assertEquals(savedCity.getName(),cityDTO.getName());
    }
    @Test
    void delete(){
        City city = cityRepository.save(newCity());
        cityService.delete(city.getId());
        assertFalse(cityRepository.existsById(city.getId()));

    }
    @Test
    void edit(){
        City city = cityRepository.save(newCity());
        CityDTO cityDTO = newCityDTO();

        CityDTO newCity = cityService.edit(city.getId(),cityDTO);
        assertEquals(newCity.getName(),cityDTO.getName());
    }
}
