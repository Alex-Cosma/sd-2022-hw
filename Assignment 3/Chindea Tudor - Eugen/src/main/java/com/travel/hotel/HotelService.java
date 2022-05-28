package com.travel.hotel;

import com.travel.city.CityRepository;
import com.travel.city.model.City;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final CityRepository cityRepository;
    public List<HotelDTO> findAll(){
        List<HotelDTO> hotelDTOS = hotelRepository.findAll().stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());
        List<Hotel> hotels =   hotelRepository.findAll();
        for(int i = 0; i < hotels.size();i++) {
            hotelMapper.populateCities(hotels.get(i), hotelDTOS.get(i));
        }
        return hotelDTOS;
    }

    private Hotel findById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found: " + id));
    }

    public HotelDTO create(HotelDTO hotelDTO){
        Hotel hotel = hotelMapper.fromDto(hotelDTO);
        City city = cityRepository.findByName(hotelDTO.getCityName()).get();
        hotel.setCity(city);

        HotelDTO hotelDTO1 = hotelMapper.toDto(hotelRepository.save(hotel));
        hotelMapper.populateCities(hotel, hotelDTO1);
        return hotelDTO1;

    }

    public void delete(Long id){
        Hotel hotel = findById(id);
        hotelRepository.delete(hotel);
    }

    public HotelDTO edit(Long id, HotelDTO hotelDTO) {
        Hotel actHotel = findById(id);
        actHotel.setName(hotelDTO.getName());
        actHotel.setPlaces(hotelDTO.getPlaces());
        actHotel.setPrice(hotelDTO.getPrice());
        actHotel.setCity(cityRepository.findByName(hotelDTO.getCityName()).get());
        HotelDTO hotelDTO1 =  hotelMapper.toDto(hotelRepository.save(actHotel));
        hotelMapper.populateCities(actHotel,hotelDTO1);
        return hotelDTO1;
    }
}
