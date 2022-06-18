package com.app.bookingapp.service.integration;


import com.app.bookingapp.data.dto.mapper.BookMapper;
import com.app.bookingapp.data.dto.mapper.BookMapperImpl;
import com.app.bookingapp.data.dto.model.BookDto;
import com.app.bookingapp.data.sql.entity.*;
import com.app.bookingapp.data.sql.entity.enums.EAccountType;
import com.app.bookingapp.data.sql.entity.enums.ERole;
import com.app.bookingapp.data.sql.repo.*;
import com.app.bookingapp.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.app.bookingapp.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookServiceIntegrationTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    private BookMapper bookMapper;

    private User user;
    private Property property;
    private Role role;
    private AccountType accountType;

    @BeforeEach
    void setUp(){
        bookMapper = new BookMapperImpl();

        bookRepository.deleteAll();
        propertyRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        accountTypeRepository.deleteAll();

        role = saveRole();
        accountType = saveAccountType();
        user = saveUser();
        property = saveProperty();
    }

    @Test
    void testFindAll() {

        int noBooks = 10;
        List<Book> books = new ArrayList<>();

        for (int i = 0; i < noBooks; i++) {
            books.add(Book.builder()
                    .user(user)
                    .property(property)
                    .date(randomDate())
                    .noSqlId(randomString())
                    .build());
        }
        bookRepository.saveAll(books);

        List<BookDto> all = bookService.findAll();

        assertEquals(noBooks, all.size());
    }

    @Test
    void testAllBooksByUser(){
        int noBooks = 10;
        List<Book> books = new ArrayList<>();

        for (int i = 0; i < noBooks; i++) {
            books.add(Book.builder()
                    .user(user)
                    .property(property)
                    .date(randomDate())
                    .noSqlId(randomString())
                    .build());
        }
        bookRepository.saveAll(books);

        List<BookDto> foundProperties = bookService.allBooksByUser(user.getUsername());
        assertEquals(noBooks, foundProperties.size());
    }

    @Test
    void testCreate(){
        Book book = Book.builder()
                .user(user)
                .property(property)
                .date(randomDate())
                .noSqlId(randomString())
                .build();

        BookDto bookDto = bookMapper.bookToBookDto(book);

        BookDto savedBookDto = bookService.create(bookDto);

        assertEquals(savedBookDto, bookDto);        //TODO problems with float values
    }

    @Test
    void testUpdateBook(){
        Book book = Book.builder()
                .user(user)
                .property(property)
                .date(randomDate())
                .noSqlId(randomString())
                .build();

        BookDto bookDto = bookMapper.bookToBookDto(book);

        BookDto savedBookDto = bookService.update(1L, bookDto);

        assertEquals(savedBookDto, bookDto);        //TODO problems with float values
    }

    private User saveUser(){

        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        return userRepository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .password(password)
                .role(role)
                .accountType(accountType)
                .phoneNumber(randomString().substring(0, 10))
                .noSqlId(randomLong())
                .build());
    }

    private Role saveRole(){
        return roleRepository.save(Role.builder()
                .name(ERole.CLIENT)
                .description(randomString())
                .build());
    }

    private AccountType saveAccountType(){
        return accountTypeRepository.save(AccountType.builder()
                .name(EAccountType.BASIC)
                .description(randomString())
                .build());
    }

    private Property saveProperty(){
        return propertyRepository.save(Property.builder()
                .name(randomString())
                .address(randomString())
                .description(randomString())
                .owner(user)
                .kitchen(randomLong())
                .numberOfBathrooms(randomLong())
                .numberOfBeds(randomLong())
                .numberOfRooms(randomLong())
                .picture(randomBytes())
                .price(randomFloat())
                .rating(randomFloat())
                .description(randomString())
                .picturesId(randomLong())
                .build());
    }
}
