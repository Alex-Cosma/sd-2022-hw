package com.example.demo.book;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.bookreview.BookReviewMapper;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class BookstoreServiceTest {

    @Mock
    private BookstoreService bookstoreService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookReviewMapper bookReviewMapper;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookstoreService = new BookstoreService(bookRepository, bookMapper, reportServiceFactory,bookReviewMapper );
    }

    @Test
    void findAll() {
        List<Book> items = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(items);

        List<BookDTO> all = bookstoreService.findAll();
        Assertions.assertEquals(items.size(), all.size());
    }

    @Test
    void findById(){
        Book book = TestCreationFactory.newBook();
        Long id =1L;
        when(bookRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(book));

        Optional<Book> book2= bookRepository.findById(id);
        Assertions.assertEquals(book.getId(),book2.get().getId() );
    }

    @Test
    void decreaseQuantity(){
//        Book book = TestCreationFactory.newBook();
//        System.out.println(book);
//        Integer quantity = book.getQuantity() - 1;
//        System.out.println(bookRepository.save(book));
//        Book book2 = bookRepository.save(book);
//
//        System.out.println(book2.getId());
//
//        when(bookstoreService.decreaseBookQuantity(book.getId(), bookMapper.toDto(book))).thenReturn(true);
//
//        Assertions.assertEquals(quantity, bookstoreService.findById(book.getId()).getQuantity());
    }

    @Test
    void searchItems() {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        String str = "1";
        when(bookstoreService.searchItems(str)).thenReturn(books);

        List<BookDTO> all = bookstoreService.searchItems(str);
        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create() {
        BookDTO book = TestCreationFactory.newBookDTO();
        when(bookstoreService.create(book)).thenReturn(book);
        Assertions.assertEquals(book, bookstoreService.create(book));
    }

    @Test
    void edit(){
//        BookDTO bookDTO = TestCreationFactory.newBookDTO();
//        System.out.println(bookDTO.getId());
//
//        when(bookstoreService.create(bookDTO)).thenReturn(bookDTO);
//        System.out.println(bookstoreService.create(bookDTO).getId());
//        System.out.println(bookDTO.getId());
//        when(bookstoreService.edit(bookDTO.getId(), bookDTO)).thenReturn(bookDTO);
    }

    @Test
    void delete(){
//        BookDTO bookDTO = TestCreationFactory.newBookDTO();
//        doNothing().when(bookstoreService).delete(bookDTO.getId());
//        verify(bookstoreService, times(1)).delete(bookDTO.getId());

//        Assertions.assertTrue(bookRepository.findById(bookDTO.getId()).isEmpty());
    }

    @Test
    void export() {
//        final String csv = "src/main/resources/csvReport.csv";
//        final String pdf = "src/main/resources/pdfReport.pdf";
//
//        when(reportServiceFactory.getReportService(ReportType.CSV).export()).thenReturn(csv);
//        when(reportServiceFactory.getReportService(ReportType.PDF).export()).thenReturn(pdf);
//
//        assertEquals(csv, reportServiceFactory.getReportService(ReportType.CSV).export());
//        assertEquals(pdf, reportServiceFactory.getReportService(ReportType.PDF).export());
    }



}