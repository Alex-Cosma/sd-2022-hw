package com.app.bookingapp.repo;

import com.app.bookingapp.data.sql.entity.*;
import com.app.bookingapp.data.sql.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.bookingapp.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {
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

    Role role;
    AccountType accountType;
    User user;
    Property property;

    @BeforeEach
    public void beforeEach() {
        bookRepository.deleteAll();
        propertyRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        accountTypeRepository.deleteAll();

        role = roleRepository.save(buildRole());
        accountType = accountTypeRepository.save(buildAccountType());
        user = userRepository.save(buildUser(role, accountType));
        property = propertyRepository.save(buildProperty());
    }

    @Test
    public void testCreate() {
//        User userSaved = saveUser();
//        Property propertySaved = saveProperty();

        Book savedBook = bookRepository.save(buildBook(user, property));

        assertNotNull(savedBook);

        assertThrows(DataIntegrityViolationException.class, () -> {
            bookRepository.save(Book.builder()
                    .build());
        });
    }

    @Test
    public void testAllBooksByUser(){
        //User userSaved = saveUser();

        int noBooks = 10;
        List<Book> books = new ArrayList<>();

        for (int i = 0; i < noBooks; i++) {
            books.add(buildBook(user, property));
        }
        bookRepository.saveAll(books);

        List<Book> all = bookRepository.findAllByUserUsername(user.getUsername());
        assertEquals(books.size(), all.size());
    }

    @Test
    public void testDeleteById(){
//        User userSaved = saveUser();
//        Property propertySaved = saveProperty();

        Book savedBook = bookRepository.save(buildBook(user, property));

        bookRepository.deleteById(savedBook.getId());

        Optional<Book> result = bookRepository.findById(savedBook.getId());
        assertTrue(result.isEmpty());
    }



//    private User saveUser(){
//        Role role = saveRole();
//        AccountType accountType = saveAccountType();
//
//        String email = "email@employee.com";
//        String password = "Abcdefg1234!";
//        return userRepository.save(User.builder()
//                .firstName(randomString())
//                .lastName(randomString())
//                .username(randomString())
//                .email(email)
//                .password(password)
//                .role(role)
//                .accountType(accountType)
//                .phoneNumber(randomString().substring(0, 10))
//                .noSqlId(randomLong())
//                .build());
//    }
//
//    private Role saveRole(){
//        return roleRepository.save(Role.builder()
//                .name(ERole.CLIENT)
//                .description(randomString())
//                .build());
//    }
//
//    private AccountType saveAccountType(){
//        return accountTypeRepository.save(AccountType.builder()
//                .name(EAccountType.BASIC)
//                .description(randomString())
//                .build());
//    }
//
//    private Property saveProperty(){
//        return propertyRepository.save(Property.builder()
//                .name(randomString())
//                .address(randomString())
//                .description(randomString())
//                .owner(saveUser())
//                .kitchen(randomLong())
//                .numberOfBathrooms(randomLong())
//                .numberOfBeds(randomLong())
//                .numberOfRooms(randomLong())
//                .picture(randomBytes())
//                .price(randomFloat())
//                .rating(randomFloat())
//                .description(randomString())
//                .picturesId(randomLong())
//                .build());
//    }

}
