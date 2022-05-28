package com.travel.BookingHotel;

import com.travel.BookingFlight.model.BookingFlight;
import com.travel.BookingFlight.model.dto.BookingFlightDTO;
import com.travel.BookingHotel.model.BookingHotel;
import com.travel.BookingHotel.model.dto.BookingHotelDTO;
import com.travel.city.model.City;
import com.travel.hotel.HotelRepository;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import com.travel.user.UserRepository;
import com.travel.user.dto.UserDTO;
import com.travel.user.mapper.UserMapper;
import com.travel.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingHotelSevice {
    private final BookingHotelRepository bookingHotelRepository;
    private final BookingHotelMapper bookingHotelMapper;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public List<BookingHotelDTO> findAll() {
        List<BookingHotelDTO> bookingHotelDTO = bookingHotelRepository.findAll().stream()
                .map(bookingHotelMapper::toDto)
                .collect(Collectors.toList());
        List<BookingHotel> bookingHotels = bookingHotelRepository.findAll();
        for (int i = 0; i < bookingHotels.size(); i++) {
            bookingHotelMapper.populateHotels(bookingHotels.get(i), bookingHotelDTO.get(i));
            bookingHotelMapper.populateUsers(bookingHotels.get(i), bookingHotelDTO.get(i));
        }
        return bookingHotelDTO;
    }

    private BookingHotel findById(Long id) {
        return bookingHotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found: " + id));
    }

    public BookingHotelDTO bookRoom(BookingHotelDTO bookingHotelDTO) {
        if (bookingHotelRepository.findByStartDateAndEndDateAndHotel(bookingHotelDTO.getStartDate(), bookingHotelDTO.getEndDate(), hotelRepository.findByName(bookingHotelDTO.getHotelName()).get()).isPresent()) {
            BookingHotel bookingHotel = bookingHotelRepository.findByStartDateAndEndDateAndHotel(bookingHotelDTO.getStartDate(), bookingHotelDTO.getEndDate(), hotelRepository.findByName(bookingHotelDTO.getHotelName()).get()).get();
            if (bookingHotel.getPlaces() > 1 && !bookingHotel.getUsers().contains(userRepository.findByUsername(bookingHotelDTO.getUserNames().stream().collect(Collectors.toList()).get(0)).get())) {
                bookingHotel.setPlaces(bookingHotel.getPlaces() - 1);
                bookingHotel.getUsers().add(userRepository.findByUsername(bookingHotelDTO.getUserNames().stream().collect(Collectors.toList()).get(0)).get());
                BookingHotelDTO bookingHotelDTO1 = bookingHotelMapper.toDto(bookingHotelRepository.save(bookingHotel));
                bookingHotelMapper.populateHotels(bookingHotel, bookingHotelDTO1);
                bookingHotelMapper.populateUsers(bookingHotel, bookingHotelDTO1);
                bookingHotelMapper.populatePlaces(bookingHotel, bookingHotelDTO1);
                return bookingHotelDTO1;
            }
            return bookingHotelDTO;
        } else {
            Hotel hotel = hotelRepository.findByName(bookingHotelDTO.getHotelName()).get();
            User user = userRepository.findByUsername(bookingHotelDTO.getUserNames().stream().collect(Collectors.toList()).get(0)).get();
            Long diff = bookingHotelDTO.getEndDate().getTime() - bookingHotelDTO.getStartDate().getTime();
            Long days = (diff / (1000 * 60 * 60 * 24));
            Long price = days * hotel.getPrice();
            System.out.println(days);
//            BookingHotel bookingHotel = BookingHotel.builder()
//                    .hotel(hotel)
//                    .users(Set.of(user))
//                    .startDate(bookingHotelDTO.getStartDate())
//                    .endDate(bookingHotelDTO.getEndDate())
//                    .places(hotel.getPlaces() - 1)
//                    .price(price).build();
            BookingHotel bookingHotel = bookingHotelMapper.fromDto(bookingHotelDTO);
            BookingHotelDTO bookingHotelDTO1 = bookingHotelMapper.toDto(bookingHotelRepository.save(bookingHotel));
            bookingHotelMapper.populateUsers(bookingHotel, bookingHotelDTO1);
            bookingHotelMapper.populateHotels(bookingHotel, bookingHotelDTO1);
            bookingHotelMapper.populatePlaces(bookingHotel, bookingHotelDTO1);
            return bookingHotelDTO1;
        }

    }

    public void delete(Long id){
        BookingHotel bookingHotel = findById(id);
        bookingHotelRepository.delete(bookingHotel);
    }
}
