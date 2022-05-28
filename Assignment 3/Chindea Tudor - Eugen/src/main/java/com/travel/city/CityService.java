package com.travel.city;

import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import com.travel.flight.model.Flight;
import com.travel.flight.model.dto.FlightDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public List<CityDTO> findAll(){
        return cityRepository.findAll().stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }
    private City findById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City not found: " + id));
    }
    public CityDTO create(CityDTO cityDTO){
        return cityMapper.toDto(cityRepository.save(cityMapper.fromDto(cityDTO)));
    }
    public void delete(Long id){
        City city = findById(id);
        cityRepository.delete(city);
    }
    public CityDTO edit(Long id, CityDTO cityDTO) {
        City actCity = findById(id);
        actCity.setName(cityDTO.getName());
        cityRepository.save(actCity);
        return cityMapper.toDto(actCity);
    }
}
