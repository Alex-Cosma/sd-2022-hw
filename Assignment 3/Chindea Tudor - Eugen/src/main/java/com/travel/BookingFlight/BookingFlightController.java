package com.travel.BookingFlight;

import com.travel.BookingFlight.model.dto.BookingFlightDTO;
import com.travel.flight.FlightService;
import com.travel.flight.model.dto.FlightDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.travel.UrlMapping.*;

@RestController
//@RequestMapping({BOOKINGF, BOOKINGF2})
@RequestMapping(BOOKINGF)
@RequiredArgsConstructor
public class BookingFlightController {
    private final BookingFlightService bookingFlightService;
    private final FlightService flightService;
//    @RequestMapping(BOOKINGF)
    @GetMapping
    public List<FlightDTO> allFlights(){
        return flightService.findAll();
    }

//    @RequestMapping(BOOKINGF2)
//    @GetMapping
//    public List<BookingFlightDTO> allBookings(){
//        return bookingFlightService.findAll();
//    }

    @PostMapping
    public BookingFlightDTO bookFlight(@RequestBody BookingFlightDTO bookingFlightDTO){
        return bookingFlightService.bookFlight(bookingFlightDTO);
    }
    @DeleteMapping(BOOKINGF_ID_PART)
    public void delete(@PathVariable Long id) { bookingFlightService.delete(id);}
}
