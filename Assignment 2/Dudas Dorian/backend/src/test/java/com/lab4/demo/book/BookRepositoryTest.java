package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        bookRepository.deleteAll();
    }

    @Test
    public void testMock(){
        Book bookSaved = bookRepository.save(Book.builder().title("test").quantity(0L).build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            bookRepository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll(){
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);
        List<Book> booksFound = bookRepository.findAll();
        assertEquals(books.size(), booksFound.size());
    }

    @Test
    public void testDeleteByID(){
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);
        List<Book> booksFound = bookRepository.findAll();
        assertEquals(books.size(), booksFound.size());
        bookRepository.deleteById(booksFound.get(0).getId());
        assertEquals(books.size() - 1, bookRepository.findAll().size());
    }

//    @Test
//    void testPaginationQuery() {
//        for (int a1 = 'a'; a1 <= 'z'; a1++) {
//            for (int a2 = 'a'; a2 <= 'z'; a2++) {
//                for (int a3 = 'a'; a3 <= 'z'; a3++) {
//                    String title = String.valueOf((char) a1) +
//                            (char) a2 +
//                            (char) a3;
//                    bookRepository.save(Book.builder()
//                            .title(title)
//                            .price(1L)
//                            .quantity(1L)
//                            .build());
//                }
//            }
//        }
//
//        final int page = 1;
//        final int pageSize = 10;
//        final PageRequest pageable = PageRequest.of(page, pageSize);
//        final Page<Book> pagedResult = bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%b%", pageable);
//
//        assertTrue(pagedResult.hasContent());
//        assertEquals(pageSize, pagedResult.getNumberOfElements());
//        assertEquals(page, pagedResult.getNumber());
//
//        // what if now we'd also want to add sorting?
//
//        final int sortedPage = 4;
//        final int sortedPageSize = 100;
//        final PageRequest first100AscByName = PageRequest.of(sortedPage, sortedPageSize, Sort.by("title").ascending());
//        final Page<Book> pagedResultSortAsc = bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%b%", first100AscByName);
//        assertTrue(pagedResultSortAsc.hasContent());
//        assertEquals(sortedPageSize, pagedResultSortAsc.getNumberOfElements());
//        assertEquals(sortedPage, pagedResultSortAsc.getNumber());
//
//        final List<Book> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
//        assertEquals(sortedPageSize, pagedResultSortedContent.size());
//
//        final Book firstItemAsc = pagedResultSortedContent.get(0);
//        pagedResultSortedContent.remove(0);
//
//        assertTrue(
//                pagedResultSortedContent.stream().anyMatch(book ->
//                        firstItemAsc.getTitle().compareTo(book.getTitle()) < 0
//                )
//        );
//    }
//
//    @Test
//    void testSearchQuery() {
//        for (int a1 = 'c'; a1 <= 'z'; a1++) {
//            for (int a2 = 'c'; a2 <= 'z'; a2++) {
//                for (int a3 = 'c'; a3 <= 'z'; a3++) {
//                    String title = String.valueOf((char) a1) +
//                            (char) a2 +
//                            (char) a3;
//                    bookRepository.save(Book.builder()
//                            .title(title)
//                            .price(1L)
//                            .quantity(1L)
//                                    .genre("bruh")
//                                    .author("blorbo")
//                            .build());
//                }
//            }
//        }
//
//        final int page = 1;
//        final int pageSize = 10;
//        final PageRequest pageable = PageRequest.of(page, pageSize);
//
//        // successful search:
//
//        Page<Book> pagedResult = bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%b%", pageable);
//
//        assertTrue(pagedResult.hasContent());
//        assertEquals(pageSize, pagedResult.getNumberOfElements());
//        assertEquals(page, pagedResult.getNumber());
//
//        // search failure:
//
//        pagedResult = bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%a%", pageable);
//
//        assertFalse(pagedResult.hasContent());
//        assertEquals(0, pagedResult.getNumberOfElements());
//        assertNotEquals(pageSize, pagedResult.getNumberOfElements());
//        assertEquals(page, pagedResult.getNumber());
//    }

//    @Test
//    void testSearchQuery(){
//        for (int a1 = 'c'; a1 <= 'z'; a1++) {
//            for (int a2 = 'c'; a2 <= 'z'; a2++) {
//                for (int a3 = 'c'; a3 <= 'z'; a3++) {
//                    String title = String.valueOf((char) a1) +
//                            (char) a2 +
//                            (char) a3;
//                    bookRepository.save(Book.builder()
//                            .title(title)
//                            .price(1L)
//                            .quantity(1L)
//                                    .genre("bruh")
//                                    .author("blorbo")
//                            .build());
//                }
//            }
//        }
//
//        List<Book> result = bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%b%");
//        assertFalse(result.isEmpty());
//        assertTrue(
//                result.stream()
//                .allMatch(book -> book.getTitle().contains("b") || book.getAuthor().contains("b") || book.getGenre().contains("b"))
//        );
//    }
}
