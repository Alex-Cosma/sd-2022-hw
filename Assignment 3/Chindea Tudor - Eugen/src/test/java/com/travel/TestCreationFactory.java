package com.travel;

import com.travel.BookingFlight.model.BookingFlight;
import com.travel.BookingFlight.model.dto.BookingFlightDTO;
import com.travel.BookingHotel.model.BookingHotel;
import com.travel.BookingHotel.model.dto.BookingHotelDTO;
import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import com.travel.flight.model.Flight;
import com.travel.flight.model.dto.FlightDTO;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import com.travel.user.dto.UserDTO;
import com.travel.user.model.User;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserDTO.class)) {
            supplier = TestCreationFactory::newUserDTO;
        } else if (cls.equals(City.class)) {
            supplier = TestCreationFactory::newCity;
        } else if (cls.equals(CityDTO.class)) {
            supplier = TestCreationFactory::newCityDTO;
        } else if (cls.equals(Flight.class)) {
            supplier = TestCreationFactory::newFlight;
        } else if (cls.equals(FlightDTO.class)) {
            supplier = TestCreationFactory::newFlightDTO;
        }else if (cls.equals(Hotel.class)) {
            supplier = TestCreationFactory::newHotel;
        } else if (cls.equals(HotelDTO.class)) {
            supplier = TestCreationFactory::newHotelDTO;
        }else if (cls.equals(BookingHotel.class)) {
            supplier = TestCreationFactory::newBookingHotel;
        } else if (cls.equals(BookingHotelDTO.class)) {
            supplier = TestCreationFactory::newBookingHotelDTO;
        }else if (cls.equals(BookingFlight.class)) {
            supplier = TestCreationFactory::newBookingFlight;
        } else if (cls.equals(BookingFlightDTO.class)) {
            supplier = TestCreationFactory::newBookingFlightDTO;
        }else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }
    public static User newUser(){
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
    }
    public static UserDTO newUserDTO() {
        return UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .build();
    }

    public static City newCity() {
        return City.builder()
                .id(randomLong())
                .name(randomString())
                .build();
    }

    public static CityDTO newCityDTO() {
        return CityDTO.builder()
                .id(randomLong())
                .name(randomString())
                .build();
    }

    public static Flight newFlight() {
        return Flight.builder()
                .id(randomLong())
                .seats(randomBoundedInt(250))
                .price(randomBoundedInt(50))
                .departureCity(newCity())
                .arrivalCity(newCity())
                .build();
    }

    public static FlightDTO newFlightDTO() {
        return FlightDTO.builder()
                .id(randomLong())
                .seats(randomBoundedInt(250))
                .price(randomBoundedInt(50))
                .departureCityName(randomString())
                .arrivalCityName(randomString())
                .build();
    }
    public static Hotel newHotel() {
        return Hotel.builder()
                .id(randomLong())
                .places(randomBoundedInt(250))
                .price(randomBoundedInt(50))
                .city(newCity())
                .name(randomString())
                .build();
    }

    public static HotelDTO newHotelDTO() {
        return HotelDTO.builder()
                .id(randomLong())
                .places(randomBoundedInt(250))
                .price(randomBoundedInt(50))
                .cityName(randomString())
                .name(randomString())
                .build();
    }
    public static BookingHotel newBookingHotel() {
        return BookingHotel.builder()
                .id(randomLong())
                .places(randomBoundedInt(250))
                .price(randomLong())
                .hotel(newHotel())
                .startDate(randomDate())
                .endDate(randomDate())
                .users(Set.of(newUser()))
                .build();
    }
    public static BookingHotelDTO newBookingHotelDTO(){
        return BookingHotelDTO.builder()
                .id(randomLong())
                .userNames(Set.of(randomString()))
                .endDate(randomDate())
                .startDate(randomDate())
                .places(randomBoundedInt(250))
                .price(randomLong())
                .hotelName(randomString())
                .build();
    }
    public static BookingFlight newBookingFlight(){
        return BookingFlight.builder()
                .id(randomLong())
                .date(randomDate())
                .seats(randomBoundedInt(250))
                .flight(newFlight())
                .users(Set.of(newUser()))
                .build();
    }
    public static BookingFlightDTO newBookingFlightDTO(){
        return BookingFlightDTO.builder()
                .id(randomLong())
                .date(randomDate())
                .seats(randomBoundedInt(250))
                .flightId(randomLong())
                .userNames(Set.of(randomString()))
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Date dateBetween(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }
    public static Date randomDate(){
        Date date = new GregorianCalendar(2022, Calendar.JUNE, 11).getTime();
        Date date2 = new GregorianCalendar(2022, Calendar.AUGUST, 11).getTime();
        return dateBetween(date,date2); }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
