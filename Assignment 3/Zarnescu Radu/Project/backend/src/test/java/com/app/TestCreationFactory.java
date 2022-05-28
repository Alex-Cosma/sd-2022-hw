package com.app;

import com.app.movie.model.EGenre;
import com.app.movie.model.Genre;
import com.app.movie.model.Movie;
import com.app.movie.model.dto.MovieDTO;
import com.app.review.model.Review;
import com.app.review.model.dto.ReviewDTO;
import com.app.user.dto.UserDTO;
import com.app.user.model.User;
import com.app.watchlist.model.Watchlist;
import com.app.watchlist.model.dto.WatchlistDTO;

import java.util.ArrayList;
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

        if (cls.equals(UserDTO.class)) {
            supplier = TestCreationFactory::newUserDTO;
        } else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else if (cls.equals(Movie.class)) {
            supplier = TestCreationFactory::newMovie;
        } else if (cls.equals(MovieDTO.class)) {
            supplier = TestCreationFactory::newMovieDTO;
        } else if (cls.equals(ReviewDTO.class)) {
            supplier = com.app.TestCreationFactory::newReviewDTO;
        } else if (cls.equals(Review.class)) {
            supplier = com.app.TestCreationFactory::newReview;
        } else if (cls.equals(Watchlist.class)) {
            supplier = com.app.TestCreationFactory::newWatchlist;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                        (T) finalSupplier.get()
                ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    public static MovieDTO newMovieDTO() {
        return MovieDTO.builder()
                .id(randomLong())
                .title(randomString())
                .backdropLink(randomString())
                .imageLink(randomString())
                .description(randomString())
                .rating(randomBoundedInt(5))
                .genres(genreSet())
                .genresString("Action Adventure")
                .build();
    }

    public static Movie newMovie() {
        return Movie.builder()
                .id(randomLong())
                .title(randomString())
                .backdropLink(randomString())
                .imageLink(randomString())
                .description(randomString())
                .rating(randomBoundedInt(5))
                .genres(genreSet())
                .build();
    }

    public static UserDTO newUserDTO() {
        return UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .password(randomString())
                .email(randomEmail())
                .build();
    }

    public static User newUser() {
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .password(randomString())
                .email(randomEmail())
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

    public static Set<Genre> genreSet() {
        Genre genre = Genre.builder().id(1L).name(EGenre.Action).build();
        Genre genre1 = Genre.builder().id(1L).name(EGenre.Adventure).build();
        return Set.of(genre, genre1);
    }

    public static ReviewDTO newReviewDTO() {
        return ReviewDTO.builder()
                .id(randomLong())
                .userId(randomLong())
                .movieId(randomLong())
                .username(randomString())
                .rating(5)
                .description(randomString())
                .build();
    }

    public static Review newReview() {
        return Review.builder()
                .id(randomLong())
                .userId(randomLong())
                .movieId(randomLong())
                .username(randomString())
                .rating(5)
                .description(randomString())
                .build();
    }

    public static ReviewDTO getReviewDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .userId(review.getUserId())
                .movieId(review.getMovieId())
                .username(review.getUsername())
                .rating(review.getRating())
                .description(review.getDescription())
                .build();
    }

    public static Watchlist newWatchlist() {
        return Watchlist.builder().id(randomLong()).movieId(randomLong()).userId(randomLong()).build();
    }

    public static WatchlistDTO getWatchlistDTO(Watchlist watchlist) {
        return WatchlistDTO.builder().id(watchlist.getId()).movieId(watchlist.getMovieId()).userId(watchlist.getUserId()).build();
    }

    public static WatchlistDTO newWatchlistDTO() {
        return WatchlistDTO.builder().id(randomLong()).movieId(randomLong()).userId(randomLong()).build();
    }

    public static List<UserDTO> getUserDTO(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User u: users) {
            userDTOS.add(UserDTO.builder().id(u.getId()).username(u.getUsername()).email(u.getEmail()).build());
        }

        return userDTOS;
    }
}
