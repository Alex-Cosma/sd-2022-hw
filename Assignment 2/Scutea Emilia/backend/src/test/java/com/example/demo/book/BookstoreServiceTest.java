package com.example.demo.book;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.model.Book;
import com.example.demo.book.model.GenreType;
import com.example.demo.book.model.dto.BookDTO;

import com.example.demo.report.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class BookstoreServiceTest {

    @InjectMocks
    private BookstoreService bookstoreService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private PDFReportService pdfReportService;

    @Mock
    private CSVReportService csvReportService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookstoreService = new BookstoreService(bookRepository, bookMapper, reportServiceFactory);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookstoreService.findAll();
        assertEquals(books.size(), all.size());
    }

    @Test
    void findById(){
        Book book = TestCreationFactory.newBook();
        when(bookRepository.save(book)).thenReturn(book);

        Long id = book.getId();
        when(bookRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(book));

        Optional<Book> book2= bookRepository.findById(id);
        assertEquals(book.getId(),book2.get().getId() );
    }

    @Test
    void decreaseQuantity(){
        Book book = TestCreationFactory.newBook();
        when(bookRepository.save(book)).thenReturn(book);
        Long id = book.getId();

        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));

        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Integer updateQuantity = book.getQuantity()-1;

        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        doNothing().when(bookRepository).sellBook(id,updateQuantity);

        bookstoreService.decreaseBookQuantity(id, bookMapper.toDto(book));

        assertEquals(updateQuantity+1, bookstoreService.findById(id).getQuantity());
    }

    @Test
    void increaseQuantity(){
        Book book = TestCreationFactory.newBook();
        when(bookRepository.save(book)).thenReturn(book);
        Long id = book.getId();

        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));

        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Integer updateQuantity = book.getQuantity();

        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        doNothing().when(bookRepository).sellBook(id,updateQuantity);

        bookstoreService.increaseBookQuantity(id, bookMapper.toDto(book));

        assertEquals(updateQuantity, bookstoreService.findById(id).getQuantity());
    }

    @Test
    void searchBooks() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        Book book = TestCreationFactory.newBook();
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        String str = TestCreationFactory.randomString();

        when(bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(str, str, str)).thenReturn(books);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);

        List<BookDTO> bookList = bookstoreService.searchBooks(str);

        Assertions.assertTrue(bookList.isEmpty());
    }

    @Test
    void create() {
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Book book = TestCreationFactory.newBook();

        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.fromDto(bookDTO)).thenReturn(book);

        bookstoreService.create(bookDTO);
        assertEquals(bookDTO,  bookstoreService.create(bookDTO));
    }

    @Test
    void edit(){
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        Book book = TestCreationFactory.newBook();

        Long id = book.getId();

        when(bookRepository.save(book)).thenReturn(book);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookRepository.save(book)).thenReturn(book);


        assertEquals(bookDTO, bookstoreService.edit(book.getId(), bookDTO));
    }

    @Test
    void delete(){
        Book book = TestCreationFactory.newBook();
        when(bookRepository.save(book)).thenReturn(book);

        Long id = book.getId();
        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));
        doNothing().when(bookRepository).delete(book);

        bookstoreService.delete(id);
        Assertions.assertFalse(bookRepository.existsById(id));
    }

    @Test
    void export() {
        final String csv = "src/main/resources/csvReport.csv";
        final String pdf = "src/main/resources/pdfReport.pdf";

        when(reportServiceFactory.getReportService(ReportType.CSV)).thenReturn(csvReportService);
        when(reportServiceFactory.getReportService(ReportType.PDF)).thenReturn(pdfReportService);

        when(reportServiceFactory.getReportService(ReportType.CSV).export()).thenReturn(csv);
        when(reportServiceFactory.getReportService(ReportType.PDF).export()).thenReturn(pdf);

        assertEquals(csv, reportServiceFactory.getReportService(ReportType.CSV).export());
        assertEquals(pdf, reportServiceFactory.getReportService(ReportType.PDF).export());
    }

    @Test
    void getBooksByGenre(){
        List<Book> books = TestCreationFactory.listOf(Book.class);
        Book book= TestCreationFactory.newBook();
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        String genre = GenreType.FITNESS.name();

        when(bookRepository.saveAll(books)).thenReturn(books);
        when(bookRepository.findAllByGenreEquals(genre)).thenReturn(books);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);

        Assertions.assertEquals(books.size(), bookstoreService.getBooksByGenre(GenreType.FITNESS).size());
    }

    @Test
    void getAllGenreTypes(){
        List<GenreType> genreTypes = Arrays.asList(GenreType.values().clone());
        Assertions.assertEquals(genreTypes.size(), bookstoreService.getAllGenreTypes().size());
    }

    @Test
    void isEnoughQuantity(){
        BookDTO bookDTO = TestCreationFactory.newBookDTO();
        bookDTO.setQuantity(10);
        Integer quantity = bookDTO.getQuantity()-1;

        Assertions.assertTrue(bookstoreService.isEnoughQuantity(bookDTO,quantity));
    }

    @Test
    void getApiResponse(){
        String subject = GenreType.FITNESS.name();
        assertFalse(bookstoreService.getApiResponse(subject).isEmpty());
    }

    @Test
    void loadBooksFromExternalApi(){
        Book book = Book.builder()
                .title("title")
                .author("author")
                .imageUrl("imageUrl")
                .description("description")
                .pageCount(new Random().nextInt(100))
                .price(10.0)
                .quantity(new Random().nextInt(100))
                .genre(GenreType.ANTIQUES.name())
                .build();

        when(bookRepository.save(book)).thenReturn(book);
        bookstoreService.loadBooksFromExternalApi(true);
        Assertions.assertTrue(bookRepository.findAll().isEmpty());
    }





}