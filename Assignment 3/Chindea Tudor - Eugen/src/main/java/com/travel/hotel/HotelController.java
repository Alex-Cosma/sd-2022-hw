package com.travel.hotel;
import com.travel.hotel.model.dto.HotelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.travel.UrlMapping.*;

@RestController
@RequestMapping(HOTEL)
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public List<HotelDTO> allHotels() {
        return hotelService.findAll();
    }

    @PostMapping
    public HotelDTO create(@RequestBody HotelDTO hotelDTO) {
        return hotelService.create(hotelDTO);
    }
    @DeleteMapping(HOTEL_ID_PART)
    public void delete(@PathVariable Long id) {
        hotelService.delete(id);
    }

    @PatchMapping(HOTEL_ID_PART)
    public HotelDTO edit(@PathVariable Long id, @RequestBody HotelDTO hotelDTO) {
        return hotelService.edit(id, hotelDTO);
    }
}
