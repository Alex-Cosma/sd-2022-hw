package com.example.backend;

import com.example.backend.game.model.Game;
import com.example.backend.game.model.dto.GameDTO;
import com.example.backend.review.model.dto.ReviewDTO;
import com.example.backend.user.dto.UserDTO;
import com.example.backend.user.model.User;
import com.example.backend.wishlist.model.WishList;

import java.util.List;
import java.util.Random;
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
        } else if (cls.equals(Game.class)) {
            supplier = TestCreationFactory::newGame;
        } else if (cls.equals(GameDTO.class)) {
            supplier = TestCreationFactory::newGameDTO;
        } else if (cls.equals(ReviewDTO.class)) {
            supplier = TestCreationFactory::newReviewDTO;
        }else if (cls.equals(WishList.class)) {
            supplier = TestCreationFactory::newWishList;
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

    public static UserDTO newUserDTO() {
        return UserDTO.builder()
                /*.id(randomLong())*/
                .username(randomString())
                .email(randomEmail())
                .build();
    }

    public static Game newGame() {
        return Game.builder()
                /*.id(randomLong())*/
                .title(randomString())
                .description(randomString())
                .developer(randomString())
                .freeToGameProfileURL(randomString())
                .gameURL(randomString())
                .genre(randomString())
                .publisher(randomString())
                .releaseDate(randomString())
                .thumbnail(randomString())
                .platform(randomString())
                .build();
    }

    public static User newUser(){
        return User.builder()
                .email(randomEmail())
                .password(randomString())
                .username(randomString())
                .build();
    }
    public static WishList newWishList(){
        return WishList.builder()
                .user(newUser())
                .game(newGame())
                .build();
    }

    private static GameDTO newGameDTO() {
        return GameDTO.builder()
                /*.id(randomLong())*/
                .title(randomString())
                .description(randomString())
                .build();
    }

    public static ReviewDTO newReviewDTO() {
        return ReviewDTO.builder()
                .rating((int) randomLong())
                .text(randomString())
                .reviewer(randomString())
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
