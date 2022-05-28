package com.travel.hotel;

import com.travel.TestCreationFactory;
import com.travel.city.CityRepository;
import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static com.travel.TestCreationFactory.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.data.jpa.util.JpaMetamodel.of;

public class HotelServiceTest {

    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hotelService = new HotelService(hotelRepository,hotelMapper,cityRepository);
    }

    @Test
    void findAll() {
        List<Hotel> hotels = TestCreationFactory.listOf(Hotel.class);
        when(hotelRepository.findAll()).thenReturn(hotels);

        List<HotelDTO> all = hotelService.findAll();

        Assertions.assertEquals(hotels.size(), all.size());
    }

    @Test
    void create() {
        Hotel hotel = newHotel();
       HotelDTO hotelDTO = newHotelDTO();
       City city = newCity();
       String cityName = randomString();
       city.setName(cityName);
       hotel.setCity(city);
       hotelDTO.setCityName(city.getName());
       when(hotelMapper.toDto(hotel)).thenReturn(hotelDTO);
       when(hotelMapper.fromDto(hotelDTO)).thenReturn(hotel);
       when(hotelRepository.save(hotel)).thenReturn(hotel);
       when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
       HotelDTO newHotelDTO = hotelService.create(hotelDTO);

       assertEquals(newHotelDTO,hotelDTO);


    }

    @Test
    void delete() {
        City city = newCity();
        when(cityRepository.save(city)).thenReturn(city);
        Hotel hotel = newHotel();
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        hotel.setCity(city);
        Long id = hotel.getId();
        when(hotelRepository.findById(id)).thenReturn(Optional.of(hotel));
        doNothing().when(hotelRepository).delete(hotel);
        hotelService.delete(hotel.getId());
        assertFalse(hotelRepository.existsById(id));
    }

    @Test
    void edit() {
        Long hotelid = randomLong();
        Hotel hotel = newHotel();
        HotelDTO hotelDTO = newHotelDTO();
        City city = newCity();
        String cityName = randomString();
        city.setName(cityName);
        hotel.setCity(city);
        hotel.setId(hotelid);
        hotelDTO.setCityName(city.getName());
        when(hotelRepository.findById(hotelid)).thenReturn(Optional.of(hotel));
        when(hotelMapper.toDto(hotel)).thenReturn(hotelDTO);
        when(hotelMapper.fromDto(hotelDTO)).thenReturn(hotel);
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
        HotelDTO newHotelDTO = hotelService.edit(hotelid,hotelDTO);

        assertEquals(newHotelDTO,hotelDTO);

    }
}
