package com.assignment2.bookstoreapp;

import com.assignment2.bookstoreapp.book.model.Book;
import com.assignment2.bookstoreapp.book.model.dto.BookDTO;
import com.assignment2.bookstoreapp.user.dto.UserListDTO;

import java.util.ArrayList;
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

        if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Book.class)) {
            supplier = TestCreationFactory::newBook;
        } else if (cls.equals(BookDTO.class)) {
            supplier = TestCreationFactory::newBookDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    public static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .build();
    }

    public static Book newBook() {
        return Book.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .price(randomDouble())
                .quantity(randomBoundedInt(1000))
                .build();
    }

    public static BookDTO newBookDTO() {
        return BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomDouble())
                .quantity(randomBoundedInt(1000))
                .build();
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static double randomDouble(){
        return new Random().nextDouble();
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

    public static ArrayList<Book>  createList(){
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Pride and Prejudice", "Jane Austen", "ROMANCE",20, 30));
        books.add(new Book(1L, "War and Peace", "Lev Tolstoi", "CLASSIC",5, 40));
        books.add(new Book(1L, "The Art of War", "Sun Tzu", "WAR",0, 100));
        books.add(new Book(1L, "The Stranger", "Albert Camus", "PHILOSOPHY",0, 46));

        return books;
    }
}