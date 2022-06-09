package com.rdaniel.sd.a2.backend;

import com.rdaniel.sd.a2.backend.book.dto.BookDto;
import com.rdaniel.sd.a2.backend.book.model.Book;
import com.rdaniel.sd.a2.backend.user.dto.RegularUserDto;
import com.rdaniel.sd.a2.backend.user.dto.UserListDto;
import com.rdaniel.sd.a2.backend.user.model.User;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

  @SuppressWarnings("all")
  public static <T> List<T> listOf(Class cls, int bound) {
    return listOf(cls, bound, (Object[]) null);
  }

  @SuppressWarnings("all")
  public static <T> List<T> listOf(Class cls, int bound, Object... parameters) {
    int nrElements;
    if (bound == 0) {
      nrElements = new Random().nextInt(10) + 5;
    } else {
      nrElements = bound;
    }

    Supplier<?> supplier;

    if (cls.equals(UserListDto.class)) {
      supplier = TestCreationFactory::newUserListDto;
    } else if (cls.equals(User.class)) {
      supplier = TestCreationFactory::newUser;
    } else if (cls.equals(Book.class)) {
      supplier = TestCreationFactory::newBook;
    } else if (cls.equals(BookDto.class)) {
      supplier = TestCreationFactory::newBookDto;
    } else {
      supplier = () -> new String("You failed.");
    }

    Supplier<?> finalSupplier = supplier;
    return IntStream.range(0, nrElements).mapToObj(i ->
            (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
        .stream().collect(toList());
  }

  public static User newUser() {
    return User.builder()
        .id(randomLong())
        .username(randomString())
        .password(randomString())
        .email(randomString() + "@" + "email.com")
        .build();
  }

  public static RegularUserDto newRegularUserDto() {
    return RegularUserDto.builder()
        .name(randomString())
        .email(randomString() + "@" + "email.com")
        .password(randomString())
        .build();
  }
  public static UserListDto newUserListDto() {
    return UserListDto.builder()
        .id(randomLong())
        .name(randomString())
        .email(randomEmail())
        .build();
  }

  public static Book newBook() {
    return Book.builder()
        .id(randomLong())
        .title(randomString())
        .author(randomString())
        .genre(randomString())
        .quantity(randomBoundedInt(100))
        .price(randomBoundedDouble(250D))
        .build();
  }

  public static BookDto newBookDto() {
    return BookDto.builder()
        .id(randomLong())
        .author(randomString())
        .title(randomString())
        .genre(randomString())
        .price(randomBoundedDouble(250D))
        .quantity(randomBoundedInt(100))
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

  public static double randomBoundedDouble(double upperBound) {
    double lowerBound = 2D;
    double nr =  lowerBound + new Random().nextDouble() * (upperBound - lowerBound);
    return Double.parseDouble(String.format(Locale.ROOT,"%.2f", nr));
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
