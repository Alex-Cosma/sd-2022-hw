package com.lab4.demo;

import com.lab4.demo.book.dto.BookDTO;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.EGenre;
import com.lab4.demo.book.model.Genre;
import com.lab4.demo.order.dto.OrderDTO;
import com.lab4.demo.order.model.Order;
import com.lab4.demo.user.dto.UserCreateDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.User;

import java.sql.Date;
import java.util.*;
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

        if (cls.equals(Book.class)) {
            supplier = TestCreationFactory::newBook;
        } else if (cls.equals(BookDTO.class)) {
            supplier = TestCreationFactory::newBookDTO;
        } else if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else if (cls.equals(Order.class)) {
            supplier = TestCreationFactory::newOrder;
        } else if (cls.equals(OrderDTO.class)) {
            supplier = TestCreationFactory::newOrderDTO;
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

    public static List<Genre> listOfGenres() {

        List<Genre> genres = new ArrayList<>();
        int id = 1;
        for(EGenre eGenre : EGenre.values()) {
            Genre genre = new Genre((long) id, eGenre);
            genres.add(genre);
            id++;
        }

        return genres;
    }

    public static Order newOrder() {
        User user = newUser();
        user.setId(randomLong());
        List<Book> books = listOf(Book.class);
        for (Book book : books) {
            book.setId(randomLong());
        }
        Set<Book> bookSet = new HashSet<>(books);
        return Order.builder()
                .id(randomLong())
                .user(user)
                .books(bookSet)
                .deliveryDate(new Date(System.currentTimeMillis()))
                .returnDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7 * 2))
                .address(randomString())
                .build();
    }

    public static OrderDTO newOrderDTO() {
        User user = newUser();
        user.setId(randomLong());
        List<Book> books = listOf(Book.class);
        for (Book book : books) {
            book.setId(randomLong());
        }
        Set<Long> bookIdsSet = books.stream().map(Book::getId).collect(Collectors.toSet());
        return OrderDTO.builder()
                .id(randomLong())
                .userId(user.getId())
                .bookIds(bookIdsSet)
                .deliveryDate(new Date(System.currentTimeMillis()))
                .returnDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7 * 2))
                .address(randomString())
                .build();
    }

    private static List<String> stringListOfGenres() {
        List<String> genres = new ArrayList<>();
        for(EGenre eGenre : EGenre.values()) {
            genres.add(eGenre.toString());
        }
        return genres;
    }

    private static List<String> stringListOfRoles() {
        List<String> roles = new ArrayList<>();
        for(ERole eRole : ERole.values()) {
            roles.add(eRole.toString());
        }
        return roles;
    }

    public static Book newBook() {
        return Book.builder()
                .title(randomString())
                .author(randomString())
                .pages(randomInt())
                .quantity(randomInt())
                .year(randomInt())
                .description(randomString())
                .build();
    }

    public static BookDTO newBookDTO() {
        List<String> genres = stringListOfGenres();
        return BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .pages(randomInt())
                .quantity(randomInt())
                .year(randomInt())
                .description(randomString())
                .genre(genres.get(randomInt()%genres.size()))
                .build();
    }

    public static UserListDTO newUserListDTO() {
        List<String> roles = stringListOfRoles();
        Set<String> rolesSet = new HashSet<>();
        String role = roles.get(randomInt()%roles.size());
        rolesSet.add(role);
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .password(randomString())
                .roles(rolesSet)
                .build();
    }

    public static UserCreateDTO newUserCreateDTO() {
        return UserCreateDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .password(randomString())
                .role("CUSTOMER")
                .build();
    }

    public static User newUser() {
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
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

    public static int randomInt() {
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
