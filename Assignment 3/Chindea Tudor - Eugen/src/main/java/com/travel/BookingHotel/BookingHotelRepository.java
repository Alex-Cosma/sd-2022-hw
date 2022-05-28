package com.travel.BookingHotel;

import com.travel.BookingHotel.model.BookingHotel;
import com.travel.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface BookingHotelRepository extends JpaRepository<BookingHotel, Long> {

    Optional<BookingHotel> findByStartDateAndEndDateAndHotel(Date startDate, Date endDate, Hotel hotel);


}
