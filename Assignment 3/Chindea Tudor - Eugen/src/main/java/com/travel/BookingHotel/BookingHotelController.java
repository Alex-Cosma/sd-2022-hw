package com.travel.BookingHotel;

import com.travel.BookingHotel.model.dto.BookingHotelDTO;
import com.travel.hotel.HotelService;
import com.travel.hotel.model.dto.HotelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.travel.UrlMapping.*;

@RestController
//@RequestMapping({BOOKINGH,BOOKINGH2})
@RequestMapping(BOOKINGH)
@RequiredArgsConstructor
public class BookingHotelController {

    private final BookingHotelSevice bookingHotelSevice;

    private final HotelService hotelService;

//    @RequestMapping(BOOKINGH)
    @GetMapping
    public List<HotelDTO> allHotels() {
        return hotelService.findAll();
    }

//    @RequestMapping(BOOKINGH2)
//    @GetMapping
//    public List<BookingHotelDTO> allBookings(){return  bookingHotelSevice.findAll();}

    @PostMapping
    public BookingHotelDTO bookRoom(@RequestBody BookingHotelDTO bookingHotelDTO) {
        System.out.println(bookingHotelDTO.getEndDate());
        return bookingHotelSevice.bookRoom(bookingHotelDTO);
    }
    @DeleteMapping(BOOKINGH_ID_PART)
    public void delete(@PathVariable Long id) { bookingHotelSevice.delete(id);}
}
