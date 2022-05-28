package com.travel.BookingFlight;

import com.travel.BookingFlight.model.BookingFlight;
import com.travel.BookingFlight.model.dto.BookingFlightDTO;
import com.travel.BookingHotel.model.BookingHotel;
import com.travel.BookingHotel.model.dto.BookingHotelDTO;
import com.travel.flight.FlightRepository;
import com.travel.flight.model.Flight;
import com.travel.user.UserRepository;
import com.travel.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingFlightService {
    private final BookingFlightMapper bookingFlightMapper;
    private final BookingFlightRepository bookingFlightRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public List<BookingFlightDTO> findAll() {
        List<BookingFlightDTO> bookingFlightDTO = bookingFlightRepository.findAll().stream()
                .map(bookingFlightMapper::toDto)
                .collect(Collectors.toList());
        List<BookingFlight> bookingFlights = bookingFlightRepository.findAll();
        for (int i = 0; i < bookingFlights.size(); i++) {
            bookingFlightMapper.populateFlights(bookingFlights.get(i), bookingFlightDTO.get(i));
            bookingFlightMapper.populateUsers(bookingFlights.get(i), bookingFlightDTO.get(i));
        }
        return bookingFlightDTO;
    }

    public BookingFlightDTO bookFlight(BookingFlightDTO bookingFlightDTO) {
        System.out.println(bookingFlightDTO.getFlightId());
        System.out.println(bookingFlightDTO.getUserNames());
        if (bookingFlightRepository.findByDateAndFlight(bookingFlightDTO.getDate(), flightRepository.findById(bookingFlightDTO.getFlightId()).get()).isPresent()) {
            BookingFlight bookingFlight = bookingFlightRepository.findByDateAndFlight(bookingFlightDTO.getDate(), flightRepository.findById(bookingFlightDTO.getFlightId()).get()).get();
            if (bookingFlight.getSeats() > 1 && bookingFlight.getUsers().contains(userRepository.findByUsername(bookingFlightDTO.getUserNames().stream().collect(Collectors.toList()).get(0)).get())) {
                bookingFlight.setSeats(bookingFlight.getSeats() - 1);
                bookingFlight.getUsers().add(userRepository.findByUsername(bookingFlightDTO.getUserNames().stream().collect(Collectors.toList()).get(0)).get());
                BookingFlightDTO bookingFlightDTO1 = bookingFlightMapper.toDto(bookingFlightRepository.save(bookingFlight));
                bookingFlightMapper.populateFlights(bookingFlight, bookingFlightDTO1);
                bookingFlightMapper.populateUsers(bookingFlight, bookingFlightDTO1);
                bookingFlightMapper.populateSeats(bookingFlight, bookingFlightDTO1);
                return bookingFlightDTO1;
            }
            return bookingFlightDTO;
        } else {
            //Flight flight = flightRepository.findById(bookingFlightDTO.getFlightId()).get();
            //User user = userRepository.findByUsername(bookingFlightDTO.getUserNames().stream().collect(Collectors.toList()).get(0)).get();
//            BookingFlight bookingFlight = BookingFlight.builder().flight(flight)
//                    .date(bookingFlightDTO.getDate())
//                    .seats(flight.getSeats() - 1)
//                    .users(Set.of(user))
//                    .date(bookingFlightDTO.getDate()).build();
            BookingFlight bookingFlight = bookingFlightMapper.fromDto(bookingFlightDTO);
            BookingFlightDTO bookingFlightDTO1 = bookingFlightMapper.toDto(bookingFlightRepository.save(bookingFlight));
            bookingFlightMapper.populateFlights(bookingFlight,bookingFlightDTO1);
            bookingFlightMapper.populateUsers(bookingFlight,bookingFlightDTO1);
            bookingFlightMapper.populateSeats(bookingFlight, bookingFlightDTO1);
            return bookingFlightDTO1;

        }
    }
    private BookingFlight findById(Long id){
        return bookingFlightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found: " + id));
    }
    public void delete(Long id){
        BookingFlight bookingFlight = findById(id);
        bookingFlightRepository.delete(bookingFlight);
    }

}






































