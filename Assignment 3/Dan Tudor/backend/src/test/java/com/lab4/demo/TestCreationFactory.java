package com.lab4.demo;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.review.model.dto.ReviewDTO;
import com.lab4.demo.stock.model.Stock;
import com.lab4.demo.stock.model.dto.StockDTO;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;

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
        } else if (cls.equals(UserDTO.class)) {
            supplier = TestCreationFactory::newUserDTO;
        } else if (cls.equals(Item.class)) {
            supplier = TestCreationFactory::newItem;
        } else if (cls.equals(ItemDTO.class)) {
            supplier = TestCreationFactory::newItemDTO;
        } else if (cls.equals(ReviewDTO.class)) {
            supplier = TestCreationFactory::newReviewDTO;
        } else if (cls.equals(Book.class)) {
            supplier = TestCreationFactory::newBook;
        } else if (cls.equals(BookDTO.class)) {
            supplier = TestCreationFactory::newBookDTO;
        } else if (cls.equals(Stock.class)){
            supplier = TestCreationFactory::newStock;
        } else if (cls.equals(StockDTO.class)){
            supplier = TestCreationFactory::newStockDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                        (T) finalSupplier.get()
                ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static Book newBook() {
        return Book.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(randomInt(1,500))
                .price(randomInt(10,500))
                .build();
    }

    private static BookDTO newBookDTO(){
        return BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(randomInt(1,500))
                .price(randomInt(10,500))
                .build();
    }

    private static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .build();
    }

    private static UserDTO newUserDTO() {
        return UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
    }

    private static Item newItem() {
        return Item.builder()
                .id(randomLong())
                .name(randomString())
                .description(randomString())
                .build();
    }

    private static ItemDTO newItemDTO() {
        return ItemDTO.builder()
                .id(randomLong())
                .name(randomString())
                .description(randomString())
                .build();
    }

    private static ReviewDTO newReviewDTO() {
        return ReviewDTO.builder()
                .id(randomLong())
                .text(randomString())
                .reviewer(randomString())
                .build();
    }

    private static Stock newStock() {
        return Stock.builder()
                .id(randomLong())
                .name(randomString())
                .symbol(randomString())
                .build();
    }

    private static StockDTO newStockDTO() {
        return StockDTO.builder()
                .id(randomLong())
                .name(randomString())
                .symbol(randomString())
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
    public static int randomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
