package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.EGenre;
import com.lab4.demo.book.model.Genre;
import com.lab4.demo.book.repository.BookRepository;
import com.lab4.demo.book.repository.GenreRepository;
import com.lab4.demo.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.lab4.demo.book.model.EGenre.Fantasy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository repository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        repository.deleteAll();
        genreRepository.deleteAll();
        for(EGenre value : EGenre.values()) {
            genreRepository.save(
                    Genre.builder()
                            .name(value)
                            .build()
            );
        }
    }

    @Test
    void findAll() {
        assertEquals(0, repository.findAll().size());
        List<Book> books = TestCreationFactory.listOf(Book.class);
        for (Book book : books) {
            book.setGenre(genreRepository.findByName(Fantasy));
        }
        repository.saveAll(books);
        List<Book> booksFromRepo = repository.findAll();
        assertEquals(books.size(), booksFromRepo.size());
        for (Book book : books) {
            assertEquals(book.getTitle(), booksFromRepo.get(books.indexOf(book)).getTitle());
            assertEquals(book.getAuthor(), booksFromRepo.get(books.indexOf(book)).getAuthor());
            assertEquals(book.getYear(), booksFromRepo.get(books.indexOf(book)).getYear());
            assertEquals(book.getPages(), booksFromRepo.get(books.indexOf(book)).getPages());
        }
    }

}