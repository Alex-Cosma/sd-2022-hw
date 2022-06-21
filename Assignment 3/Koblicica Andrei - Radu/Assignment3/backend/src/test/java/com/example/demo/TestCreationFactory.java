package com.example.demo;

import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import com.example.demo.review.dto.ReviewDTO;
import com.example.demo.review.model.Review;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.dto.UserListDTO;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.demo.user.model.ERole.REGULAR_USER;
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

        if (cls.equals(Movie.class)) {
            supplier = TestCreationFactory::newMovie;
        } else if (cls.equals(MovieDTO.class)) {
            supplier = TestCreationFactory::newMovieDTO;
        } else if(cls.equals(User.class)){
            supplier = TestCreationFactory::newUser;
        } else if(cls.equals(UserDTO.class)){
            supplier = TestCreationFactory::newUserDTO;
        } else if(cls.equals(Review.class)){
            supplier = TestCreationFactory::newReview;
        } else if(cls.equals(ReviewDTO.class)){
            supplier = TestCreationFactory::newReviewDTO;
        }
        else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    public static Movie newMovie() {
        return Movie.builder()
                .id(randomLong())
                .title(randomString())
                .description(randomString())
                .genre(randomString())
                .year(randomBoundedInt(2021))
                .rating(1+randomFloat())
                .imagePath(randomString())
                .build();
    }

    public static MovieDTO newMovieDTO() {
        return MovieDTO.builder()
                .id(randomLong())
                .title(randomString())
                .description(randomString())
                .genre(randomString())
                .year(randomBoundedInt(2021))
                .rating(randomFloat())
                .imagePath(randomString())
                .build();
    }

    public static Review newReview() {
        return Review.builder()
                .userId(randomLong())
                .movieId(randomLong())
                .username(randomString())
                .rating(randomBoundedInt(10))
                .content(randomString())
                .build();
    }

    public static ReviewDTO newReviewDTO() {
        return ReviewDTO.builder()
                .userId(randomLong())
                .movieId(randomLong())
                .username(randomString())
                .rating(randomBoundedInt(10))
                .content(randomString())
                .build();
    }

    public static User newUser(){
        Set<Role> roles=new HashSet<>();
        Role role=new Role();
        role.setName(REGULAR_USER);
        roles.add(role);
        return User.builder()
                .email(randomEmail())
                .username(randomString())
                .password("WooHoo1!")
                //.roles(roles)
                .build();
    }

    public static UserDTO newUserDTO(){
        Set<String> roles=new HashSet<String>();
        roles.add("REGULAR_USER");
        return UserDTO.builder()
                .id(randomLong())
                .email(randomEmail())
                .username(randomString())
                .password("WooHoo1!")
                .roles(roles)
                .build();
    }

    public static ReviewDTO toDTO(Review review){
        return ReviewDTO.builder()
                .id(review.getId())
                .userId(review.getUserId())
                .movieId(review.getMovieId())
                .username(review.getUsername())
                .rating(review.getRating())
                .content(review.getContent())
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

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static float randomFloat() {
        return new Random().nextInt(10);
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