package com.example.demo.book;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.model.Book;
import com.example.demo.book.model.GenreType;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookstoreServiceIntegrationTest {

    @Autowired
    private BookstoreService bookstoreService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);

        List<BookDTO> all = bookstoreService.findAll();

        Assertions.assertFalse(all.isEmpty());
    }


    @Test
    void findById() {
        Book book = TestCreationFactory.newBook();
        book = bookRepository.save(book);
        Assertions.assertEquals(book.getId(), bookstoreService.findById(book.getId()).getId());
    }

    @Test
    void decreaseBookQuantity() {
        Book book = TestCreationFactory.newBook();
        Integer quantity = book.getQuantity() - 1;

        book = bookRepository.save(book);
        bookstoreService.decreaseBookQuantity(book.getId(), bookMapper.toDto(book));

        Assertions.assertEquals(quantity, bookstoreService.findById(book.getId()).getQuantity());
    }

    @Test
    void increaseBookQuantity() {
        Book book = TestCreationFactory.newBook();
        Integer quantity = book.getQuantity() + 1;

        book = bookRepository.save(book);
        bookstoreService.increaseBookQuantity(book.getId(), bookMapper.toDto(book));

        Assertions.assertEquals(quantity, bookstoreService.findById(book.getId()).getQuantity());
    }

    @Test
    void searchBooks() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);

        List<BookDTO> bookDTOList = bookstoreService.searchBooks("FITNESS");
        assertFalse(bookDTOList.isEmpty());

        List<BookDTO> bookDTOList2 = bookstoreService.searchBooks("ART");
        Assertions.assertTrue(bookDTOList2.isEmpty());

    }

    @Test
    void create() {
        BookDTO book = TestCreationFactory.newBookDTO();
        BookDTO bookDTO = bookstoreService.create(book);
        Assertions.assertEquals(book.getTitle(), bookDTO.getTitle());
    }

    @Test
    void edit() {
        BookDTO book = TestCreationFactory.newBookDTO();
        BookDTO bookDTO = bookstoreService.create(book);
        bookDTO.setGenre(GenreType.BIBLES.name());
        Assertions.assertEquals(bookDTO.getGenre(), bookstoreService.edit(bookDTO.getId(), bookDTO).getGenre());
    }

    @Test
    void delete() {
        BookDTO book = TestCreationFactory.newBookDTO();
        BookDTO bookDTO = bookstoreService.create(book);
        bookstoreService.delete(bookDTO.getId());
        Assertions.assertTrue(bookRepository.findById(bookDTO.getId()).isEmpty());
    }

    @Test
    void export() {
        final String csv = "src/main/resources/csvReport.csv";
        final String pdf = "src/main/resources/pdfReport.pdf";

        assertEquals(csv, reportServiceFactory.getReportService(ReportType.CSV).export());
        assertEquals(pdf, reportServiceFactory.getReportService(ReportType.PDF).export());
    }

    @Test
    void getBooksByGenre(){
        Book book = TestCreationFactory.newBook();
        bookRepository.save(book);
        GenreType genreType = GenreType.FITNESS;


        Assertions.assertEquals(1, bookstoreService.getBooksByGenre(genreType).size());
    }

    @Test
    void getAllGenreTypes(){
        List<GenreType> genreTypes = Arrays.asList(GenreType.values().clone());
        Assertions.assertEquals(genreTypes, bookstoreService.getAllGenreTypes());
    }

    @Test
    void isEnoughQuantity(){
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Integer quantity = bookDTO.getQuantity();

        Assertions.assertTrue(bookstoreService.isEnoughQuantity(bookDTO,quantity));
    }

    @Test
    void getApiResponse(){
        String subject = GenreType.FITNESS.name();

        assertFalse(bookstoreService.getApiResponse(subject).isEmpty());
    }

    @Test
    void loadBooksFromExternalApi(){
        bookstoreService.loadBooksFromExternalApi(true);
        assertFalse(bookRepository.findAll().isEmpty());
    }
}
