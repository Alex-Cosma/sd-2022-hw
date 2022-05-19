package com.example.demo;

import com.example.demo.book.model.Book;
import com.example.demo.book.model.GenreType;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.bookreview.model.BookReview;
import com.example.demo.bookreview.model.Rating;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartDTO;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.User;
import com.example.demo.userreview.model.UserReview;
import com.example.demo.userreview.model.dto.UserReviewDTO;

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
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Book.class)) {
            supplier = TestCreationFactory::newBook;
        } else if (cls.equals(BookDTO.class)) {
            supplier = TestCreationFactory::newBookDTO;

        } else if (cls.equals(BookReview.class)) {
            supplier = TestCreationFactory::newBookReview;
        }
        else if (cls.equals(BookReviewDTO.class)) {
            supplier = TestCreationFactory::newBookReviewDTO;
        }
        else if (cls.equals(UserReview.class)) {
            supplier = TestCreationFactory::newUserReview;
        }
        else if (cls.equals(UserReviewDTO.class)) {
            supplier = TestCreationFactory::newUserReviewDTO;
        }
        else if (cls.equals(Cart.class)) {
            supplier = TestCreationFactory::newCart;
        }
        else if (cls.equals(CartDTO.class)) {
            supplier = TestCreationFactory::newCartDTO;
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

    private static UserDTO newUserListDTO() {
        return UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .build();
    }

    public static Cart newCart(){
        return Cart.builder()
                .user_id(randomLong())
                .id(randomLong())
                .items(List.of(newBook()))
                .build();
    }

    public static CartDTO newCartDTO(){
        return CartDTO.builder()
                .user_id(randomLong())
                .id(randomLong())
                .items(List.of(newBook()))
                .build();
    }

    public static User newUser() {
        return User.builder()
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
    }

    public static UserDTO newUserDTO() {
        return UserDTO.builder()
                .username(randomString())
                .email(randomEmail())
                .username(randomString())
                .password(randomString())
                .build();
    }

    public static BookReview newBookReview(){
        return BookReview.builder()
                .rating(Rating.AVERAGE.name())
                .text(randomString())
                .build();
    }
    public static BookReviewDTO newBookReviewDTO(){
        return BookReviewDTO.builder()
                .rating(Rating.AVERAGE.name())
                .text(randomString())
                .build();
    }

    public static UserReview newUserReview(){
        return UserReview.builder()
                .rating(Rating.AVERAGE.name())
                .text(randomString())
                .build();
    }
    public static UserReviewDTO newUserReviewDTO(){
        return UserReviewDTO.builder()
                .rating(Rating.AVERAGE.name())
                .text(randomString())
                .build();
    }

    public static Book newBook() {
        return Book.builder()
                .title(randomString())
                .author(randomString())
                .genre(GenreType.FITNESS.name())
                .quantity(randomBoundedInt(100))
                .price(10.0)
                .description(randomString())
                .imageUrl(randomString())
                .pageCount(10)
                .build();
    }

    public static BookDTO newBookDTO() {
        return BookDTO.builder()
                .id(1L)
                .title(randomString())
                .author(randomString())
                .genre(GenreType.FITNESS.name())
                .quantity(randomBoundedInt(100))
                .description(randomString())
                .imageUrl(randomString())
                .price(10.0)
                .reviews(List.of())
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