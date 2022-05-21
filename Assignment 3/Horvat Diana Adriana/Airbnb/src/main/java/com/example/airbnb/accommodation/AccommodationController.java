package com.example.airbnb.accommodation;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.airbnb.user.UrlMapping.*;

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
    public AccommodationDTO create(@RequestBody AccommodationDTO accommodationDTO){
        return accommodationService.create(accommodationDTO);
    }

    @CrossOrigin
    @PutMapping(ENTITY)
    public AccommodationDTO edit(@PathVariable Long id, @RequestBody AccommodationDTO accommodationDTO){
        return accommodationService.edit(id, accommodationDTO);
    }

    @CrossOrigin
    @GetMapping(EXPORT_ACCOMMODATION_PDF)
    public ResponseEntity<?> exportPDF(@PathVariable Long id) throws IOException {
        return accommodationService.exportPDF(id);
    }

    @CrossOrigin
    @GetMapping(SEND_EMAIL)
    public void sendEmail(@PathVariable Long id){
        accommodationService.sendEmail(id);
    }

}
