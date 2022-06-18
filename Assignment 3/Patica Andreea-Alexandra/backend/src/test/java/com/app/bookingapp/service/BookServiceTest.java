package com.app.bookingapp.service;

import com.app.bookingapp.data.dto.mapper.BookMapper;
import com.app.bookingapp.data.dto.mapper.BookMapperImpl;
import com.app.bookingapp.data.dto.model.BookDto;
import com.app.bookingapp.data.sql.entity.*;
import com.app.bookingapp.data.sql.entity.enums.EAccountType;
import com.app.bookingapp.data.sql.entity.enums.ERole;
import com.app.bookingapp.data.sql.repo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.bookingapp.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AccountTypeRepository accountTypeRepository;
    @Mock
    private PropertyRepository propertyRepository;

    private BookMapper bookMapper;

    private User user;
    private Property property;
    private Role role;
    private AccountType accountType;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapperImpl();
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper, userRepository, propertyRepository);

        role = buildRole();
        accountType = buildAccountType();
        user = buildUser();
        property = buildProperty();
    }

    @Test
    void testFindAll() {
        List<Book> books = new ArrayList<>();
        int noBooks = 10;
        for (int i = 0; i < noBooks; i++) {
            books.add(Book.builder()
                    .user(new User())
                    .property(new Property())
                    .date(randomDate())
                    .noSqlId(randomString())
                    .build());
        }

        when(bookRepository.findAll()).thenReturn(books);

        List<BookDto> all = bookService.findAll();

        assertEquals(noBooks, all.size());
    }

    @Test
    void testAllBooksByUser(){
        Long id = randomLong();
        String username = randomString();
        User user = User.builder()
                .id(id)
                .username(username)
                .build();

        int noBooks = 10;
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < noBooks; i++) {
            books.add(Book.builder()
                    .user(user)
                    .property(new Property())
                    .date(randomDate())
                    .noSqlId(randomString())
                    .build());
        }

        when(bookRepository.findAllByUserUsername(user.getUsername())).thenReturn(books);

        List<BookDto> foundBooks = bookService.allBooksByUser(user.getUsername());
        Assertions.assertEquals(noBooks, foundBooks.size());
    }

    @Test
    void testAddBook(){
        Book book = Book.builder()
                .user(user)
                .property(property)
                .date(randomDate())
                .noSqlId(randomString())
                .build();
        BookDto bookDto = bookMapper.bookToBookDto(book);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(propertyRepository.findPropertyByName(any())).thenReturn(Optional.of(property));
        when(bookRepository.save(any())).thenReturn(book);

        BookDto savedBookDto = bookService.create(bookDto);
        assertEquals(bookDto, savedBookDto);
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

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(propertyRepository.findPropertyByName(any())).thenReturn(Optional.of(property));
        when(bookRepository.save(any())).thenReturn(book);

        BookDto savedBookDto = bookService.update(randomLong(), bookDto);
        assertEquals(bookDto, savedBookDto);
    }

    @Test
    void delete(){
        Long id = randomLong();
        Book book = Book.builder()
                .user(user)
                .property(property)
                .date(randomDate())
                .noSqlId(randomString())
                .build();

        bookRepository.save(book);

        doNothing().when(bookRepository).deleteById(id);

        bookService.delete(id);

        verify(bookRepository, times(1)).deleteById(id);
    }


    private User buildUser(){

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

    private Role buildRole(){
        return Role.builder()
                .name(ERole.CLIENT)
                .description(randomString())
                .build();
    }

    private AccountType buildAccountType(){
        return AccountType.builder()
                .name(EAccountType.BASIC)
                .description(randomString())
                .build();
    }

    private Property buildProperty(){
        return Property.builder()
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
                .build();
    }
}
