package com.rdaniel.sd.project;

import com.rdaniel.sd.project.customer.dto.CustomerDto;
import com.rdaniel.sd.project.customer.model.Customer;
import com.rdaniel.sd.project.device.dto.DeviceDto;
import com.rdaniel.sd.project.device.model.Device;
import com.rdaniel.sd.project.ticket.dto.TicketDto;
import com.rdaniel.sd.project.ticket.model.Ticket;
import com.rdaniel.sd.project.ticket.model.enums.StatusType;
import com.rdaniel.sd.project.user.dto.UserDto;
import com.rdaniel.sd.project.user.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {
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

    if (cls.equals(UserDto.class)) {
      supplier = TestCreationFactory::newUserDto;
    } else if (cls.equals(User.class)) {
      supplier = TestCreationFactory::newUser;
    } else if (cls.equals(Device.class)) {
      supplier = TestCreationFactory::newDevice;
    } else if (cls.equals(Ticket.class)) {
      supplier = TestCreationFactory::newTicket;
    } else if (cls.equals(TicketDto.class)) {
      supplier = TestCreationFactory::newTicketDto;
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

  public static UserDto newUserDto() {
    return UserDto.builder()
        .id(randomLong())
        .username(randomString())
        .email(randomString() + "@" + "email.com")
        .build();
  }

  public static Customer newCustomer() {
    return Customer.builder()
        .id(randomLong())
        .username(randomString())
        .password(randomString())
        .email(randomString() + "@" + "email.com")
        .fullName(randomString())
        .address(randomString())
        .phoneNumber(randomString())
        .birthDate(LocalDate.of(1999, 5, 3))
        .build();
  }

  public static CustomerDto newCustomerDto() {
    return CustomerDto.builder()
        .id(randomLong())
        .username(randomString())
        .email(randomString() + "@" + "email.com")
        .fullName(randomString())
        .address(randomString())
        .phoneNumber(randomString())
        .birthDate(LocalDate.of(1999, 5, 20).toString())
        .build();
  }

  public static Device newDevice() {
    return Device.builder()
        .id(randomLong())
        .name(randomString())
        .brand(randomString())
        .build();
  }

  public static DeviceDto newDeviceDto() {
    return DeviceDto.builder()
        .id(randomLong())
        .name(randomString())
        .brand(randomString())
        .build();
  }

  public static Ticket newTicket() {
    return Ticket.builder()
        .id(randomLong())
        .description(randomString())
        .status(StatusType.NEW)
        .createdAt(LocalDateTime.now())
        .build();
  }

  public static TicketDto newTicketDto() {
    return TicketDto.builder()
        .id(randomLong())
        .description(randomString())
        .status(StatusType.NEW.toString())
        .createdAt(LocalDateTime.now().toString())
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
    double nr = lowerBound + new Random().nextDouble() * (upperBound - lowerBound);
    return Double.parseDouble(String.format(Locale.ROOT, "%.2f", nr));
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
