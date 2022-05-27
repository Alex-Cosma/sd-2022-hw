package com.travel.city;

import com.travel.TestCreationFactory;
import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.travel.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CityServiceTest {
    @InjectMocks
    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper cityMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cityService = new CityService(cityRepository,cityMapper);
    }

    @Test
    void findAll() {
        List<City> cities = TestCreationFactory.listOf(City.class);
        when(cityRepository.findAll()).thenReturn(cities);

        List<CityDTO> all = cityService.findAll();

        Assertions.assertEquals(cities.size(), all.size());
    }

    @Test
    void create() {
        City city = newCity();
        CityDTO cityDTO = newCityDTO();
        when(cityMapper.toDto(city)).thenReturn(cityDTO);
        when(cityMapper.fromDto(cityDTO)).thenReturn(city);
        when(cityRepository.save(city)).thenReturn(city);
        CityDTO newCityDTO = cityService.create(cityDTO);

        assertEquals(newCityDTO, cityDTO);
    }

    @Test
    void delete() {
        City city = newCity();
        when(cityRepository.save(city)).thenReturn(city);
        Long id = city.getId();
        when(cityRepository.findById(id)).thenReturn(Optional.of(city));
        doNothing().when(cityRepository).delete(city);
        cityService.delete(city.getId());
        assertFalse(cityRepository.existsById(id));
    }

    @Test
    void edit() {
        Long cityId = randomLong();
        City city = newCity();
        CityDTO cityDTO = newCityDTO();
        city.setId(cityId);
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));
        when(cityMapper.toDto(city)).thenReturn(cityDTO);
        when(cityMapper.fromDto(cityDTO)).thenReturn(city);
        when(cityRepository.save(city)).thenReturn(city);
        CityDTO newCityDTO = cityService.edit(cityId, cityDTO);

        assertEquals(newCityDTO, cityDTO);
    }
}
