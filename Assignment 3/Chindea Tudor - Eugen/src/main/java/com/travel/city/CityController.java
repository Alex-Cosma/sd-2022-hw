package com.travel.city;

import com.travel.city.model.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.travel.UrlMapping.*;
@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping
    public List<CityDTO> allCities(){return cityService.findAll();}

    @PostMapping
    public CityDTO create(@RequestBody CityDTO city) {
        return cityService.create(city);
    }
    @DeleteMapping(ITEMS_ID_PART)
    public void delete(@PathVariable Long id){
        cityService.delete(id);
    }
    @PatchMapping(ITEMS_ID_PART)
    public CityDTO edit(@PathVariable Long id, @RequestBody CityDTO item) {
        return cityService.edit(id, item);
    }

}
