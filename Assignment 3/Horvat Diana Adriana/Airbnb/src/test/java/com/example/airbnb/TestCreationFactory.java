package com.example.airbnb;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.booking.model.dto.BookingDTO;
import com.example.airbnb.review.model.dto.ReviewDTO;
import com.example.airbnb.security.dto.SignupRequest;
import com.example.airbnb.user.model.ERole;
import com.example.airbnb.user.model.Role;
import com.example.airbnb.user.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
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

//        if (cls.equals(UserListDTO.class)) {
//            supplier = TestCreationFactory::newUserListDTO;
//        } else
        if (cls.equals(Accommodation.class)) {
            supplier = TestCreationFactory::newAccommodation;
        }
//        else if (cls.equals(BookDTO.class)) {
//            supplier = TestCreationFactory::newBookDTO;}
//        } else if (cls.equals(ReviewDTO.class)) {
//            supplier = TestCreationFactory::newReviewDTO;
//        }
        else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                        (T) finalSupplier.get()
                ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static Accommodation newAccommodation() {

        return Accommodation.builder().
                name("Accommodation").description("Description")
                .house_rules("House Rules").property_type("Property Type").room_type("Room Type").bathrooms(2)
                .bedrooms(2).beds(2).price(100.1).build();
    }

    public static Accommodation accommodation(){
        return Accommodation.builder().id(randomLong()).
                name("Accommodation").description("Description")
                .house_rules("House Rules").property_type("Property Type").room_type("Room Type").bathrooms(2)
                .bedrooms(2).beds(2).price(100.1).build();
    }

    public static Accommodation accommodationWithUser(User user) {
        return Accommodation.builder().id(randomLong()).
                name("Accommodation").description("Description")
                .house_rules("House Rules").property_type("Property Type").room_type("Room Type").bathrooms(2)
                .bedrooms(2).beds(2).price(100.1).user(user).build();
    }

    public static User user(){
        return User.builder().id(randomLong())
                .email(randomEmail()).password(randomString()).username(randomString()).build();
    }

   public static Booking booking(Accommodation accommodation, User user, Long bookingId) throws ParseException {
       String date_string = "2023-02-01";
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       Date start_date = formatter.parse(date_string);

       String date_string1 = "2023-03-01";
       SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
       Date end_date = formatter.parse(date_string);

        return Booking.builder().id(bookingId).
                accommodation(accommodation()).guest(user).start_date(start_date).end_date(end_date).build();
    }

    public static Booking newBooking() throws ParseException {
        String date_string = "2023-02-01";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date = formatter.parse(date_string);

        String date_string1 = "2023-03-01";
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date end_date = formatter.parse(date_string);

        return Booking.builder().id(randomLong()).start_date(start_date).end_date(end_date).build();
    }

    public static BookingDTO bookingDTO(Long accommodation_id, Long user_id) throws ParseException {
       String date_string = "2023-02-01";
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       Date start_date = formatter.parse(date_string);

       String date_string1 = "2023-03-01";
       SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
       Date end_date = formatter.parse(date_string);

        return BookingDTO.builder().id(randomLong()).accommodation_id(accommodation_id).guest_id(user_id).start_date(start_date).end_date(end_date).build();
    }

    public static BookingDTO newBookingDTO() throws ParseException {
        String date_string = "2023-02-01";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date = formatter.parse(date_string);

        String date_string1 = "2023-03-01";
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date end_date = formatter.parse(date_string);

        return BookingDTO.builder().id(randomLong()).start_date(start_date).end_date(end_date).build();
    }

    public static AccommodationDTO accommodationDTOWithUserId(Long userId, Long accommodationId){
        return AccommodationDTO.builder().id(accommodationId).name("Accommodation").description("Description")
                .house_rules("House Rules").property_type("Property Type").room_type("Room Type").bathrooms("2").bedrooms("2")
                .beds("2").price(100.1).imageURL("imageURL").address_city("address_city").address_city("address_country")
                .amenities("amenity1, amenity2, amenity3, amenity4")
                .address_street("address_street").user_id(userId).build();
    }

    public static AccommodationDTO accommodationDTO(){
        return AccommodationDTO.builder().name("Accommodation").description("Description")
                .house_rules("House Rules").property_type("Property Type").room_type("Room Type").bathrooms("2").bedrooms("2")
                .beds("2").price(100.1).imageURL("imageURL").address_city("address_city").address_city("address_country")
                .amenities("amenity1, amenity2, amenity3, amenity4")
                .address_street("address_street").build();
    }

    public static Role role(ERole role){
        return Role.builder().id(randomInt()).name(role).build();
    }

    public static ReviewDTO reviewDTO(Long userId, Long accommodationId){
        return ReviewDTO.builder().id(randomLong()).accommodationId(accommodationId).userId(userId).content("review").build();
    }

    public static ReviewDTO newReviewDTO(){
        return ReviewDTO.builder().id(randomLong()).content("review").build();
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

    public static int randomInt() {
        return new Random().nextInt(12444);
    }

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