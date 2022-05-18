package com.example.airbnb.accommodation;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.airbnb.user.UrlMapping.ACCOMMODATIONS;
import static com.example.airbnb.user.UrlMapping.ENTITY;

@RestController
@RequestMapping(ACCOMMODATIONS)
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @CrossOrigin
    @GetMapping()
    public Page<Accommodation> getAccommodations() {
        return accommodationService.findAll();
    }


    @CrossOrigin
    @GetMapping(ENTITY)
    public Accommodation getAccommodation(@PathVariable Long id) {
        return accommodationService.findById(id);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        accommodationService.delete(id);
    }

    @CrossOrigin
    @PostMapping()
    public Accommodation create(@RequestBody AccommodationDTO accommodationDTO){
        System.out.println("sdncld");
        return accommodationService.create(accommodationDTO);
    }

}
