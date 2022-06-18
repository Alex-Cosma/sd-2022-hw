package com.app.bookingapp;

import com.app.bookingapp.data.dto.mapper.*;
import com.app.bookingapp.data.dto.model.BookDto;
import com.app.bookingapp.data.dto.model.PropertyDto;
import com.app.bookingapp.data.dto.model.UserDto;
import com.app.bookingapp.data.sql.entity.*;
import com.app.bookingapp.data.sql.entity.enums.EAccountType;
import com.app.bookingapp.data.sql.entity.enums.ERole;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
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

        if (cls.equals(BookDto.class)) {
            //supplier = TestCreationFactory::newBookDto;
            User user = (User) parameters[0];
            supplier = () -> newBookDto(user);
        } else if (cls.equals(PropertyDto.class)) {
            User user = (User) parameters[0];
            supplier = () -> newPropertyDto(user);
            //supplier = TestCreationFactory::newPropertyDto;
        } else if (cls.equals(UserDto.class)){
            supplier = TestCreationFactory::newUserDto;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                        (T) finalSupplier.get()
                ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 12;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

//    public static LocalDateTime randomLocalDateTime() {
//        LocalDateTime now = LocalDateTime.now();
//        int year = 60 * 60 * 24 * 365;
//        return now.plusSeconds((long) new Random().nextInt(-2 * year, 2 * year));// +- 2 years;
//    }

    public static Float randomFloat(){
        return new Random().nextFloat();
    }

    public static Integer randomInteger(){
        return new Random().nextInt();
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static byte[] randomBytes() {
        byte[] b = new byte[20];
        new Random().nextBytes(b);

        return b;
    }

    public static Date randomDate(){
        return new Date(ThreadLocalRandom.current().nextInt() * 1000L);

    }

    public static BookDto newBookDto() {
        BookMapper bookMapper = new BookMapperImpl();
        return bookMapper.bookToBookDto(Book.builder()
                .user(buildUser())
                .property(buildProperty())
                .date(randomDate())
                .noSqlId(randomString())
                .build());
    }

    public static BookDto newBookDto(User user) {
        BookMapper bookMapper = new BookMapperImpl();
        return bookMapper.bookToBookDto(Book.builder()
                .user(user)
                .property(buildProperty())
                .date(randomDate())
                .noSqlId(randomString())
                .build());
    }

    public static UserDto newUserDto(){
        UserMapper userMapper = new UserMapperImpl();
        return userMapper.userToUserDto(buildUser());
    }

    public static User buildUser(){

        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        return User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .password(password)
                .role(buildRole())
                .accountType(buildAccountType())
                .phoneNumber(randomString().substring(0, 10))
                .noSqlId(randomLong())
                .build();
    }

    public static User buildUser(Role role, AccountType accountType){

        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        return User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .password(password)
                .role(role)
                .accountType(accountType)
                .phoneNumber(randomString().substring(0, 10))
                .noSqlId(randomLong())
                .build();
    }


    public static Role buildRole(){
        return Role.builder()
                .name(ERole.CLIENT)
                .description(randomString())
                .build();
    }

    public static AccountType buildAccountType(){
        return AccountType.builder()
                .name(EAccountType.BASIC)
                .description(randomString())
                .build();
    }

    public static PropertyDto newPropertyDto(){
        PropertyMapper propertyMapper = new PropertyMapperImpl();
        return propertyMapper.propertyToPropertyDto(buildProperty());
    }

    public static PropertyDto newPropertyDto(User user){
        PropertyMapper propertyMapper = new PropertyMapperImpl();
        Property property = buildProperty();
        property.setOwner(user);
        return propertyMapper.propertyToPropertyDto(property);
    }

    public static Property buildProperty(){
        return Property.builder()
                .name(randomString())
                .address(randomString())
                .description(randomString())
                .owner(buildUser())
                .kitchen(randomLong())
                .numberOfBathrooms(randomLong())
                .numberOfBeds(randomLong())
                .numberOfRooms(randomLong())
                .picture(randomBytes())
                .price(randomFloat())
                .rating(randomFloat())
                .description(randomString())
                .picturesId(randomLong())
                .build();
    }


    public static Book buildBook(User user, Property property){
        return Book.builder()
                .user(user)
                .property(property)
                .date(randomDate())
                .noSqlId(randomString())
                .build();
    }
}
