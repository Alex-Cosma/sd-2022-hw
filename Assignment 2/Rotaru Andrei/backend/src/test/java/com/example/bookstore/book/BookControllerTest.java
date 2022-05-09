package com.example.bookstore.book;

import com.example.bookstore.BaseControllerTest;
import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.security.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.bookstore.TestCreationFactory.randomLong;
import static com.example.bookstore.TestCreationFactory.randomString;
import static com.example.bookstore.UrlMapping.*;
import static com.example.bookstore.report.ReportType.CSV;
import static com.example.bookstore.report.ReportType.PDF;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class BookControllerTest extends BaseControllerTest {
    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allAvailableBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAllAvailable()).thenReturn(books);

        ResultActions response = performGet(BOOKS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }

    @Test
    void filterBooks() throws Exception {
        BookDTO bookDTO1 = BookDTO.builder()
                .id(randomLong())
                .title("title1")
                .author("author1")
                .genre("genre1")
                .quantity(10)
                .price(19.99)
                .build();

        BookDTO bookDTO2 = BookDTO.builder()
                .id(randomLong())
                .title("amazingTitle")
                .author("amazingAuthor")
                .genre("amazingGenre")
                .quantity(20)
                .price(19.99)
                .build();

        List<BookDTO> filteredBooks = List.of(bookDTO2);

        when(bookService.create(bookDTO1)).thenReturn(bookDTO1);
        when(bookService.create(bookDTO2)).thenReturn(bookDTO2);
        when(bookService.filterBooks("%amazing%")).thenReturn(filteredBooks);

        ResultActions result3 = performGetWithPathVariable(BOOKS+"/filter/{filter}","%amazing%" );
        result3.andExpect(status().isOk()).andExpect(jsonContentToBe(filteredBooks));
    }

    @Test
    void exportReport() throws Exception {
        final String csv = "csv";
        final String pdf = "pdf";
        when(controller.exportReport(CSV)).thenReturn(csv);
        when(controller.exportReport(PDF)).thenReturn(pdf);

        ResultActions responseCsv = mockMvc.perform(get(BOOKS + EXPORT_REPORT, CSV.name()));
        ResultActions responsePdf = mockMvc.perform(get(BOOKS + EXPORT_REPORT, PDF.name()));

        responseCsv.andExpect(status().isOk())
                .andExpect(content().string(csv));
        responsePdf.andExpect(status().isOk())
                .andExpect(content().string(pdf));
    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder().title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(10)
                .price(19.99)
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOKS, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book created successfully")));;
    }

    @Test
    void edit() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(10)
                .price(19.99)
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        reqBook.setTitle("newTitle");
        when(bookService.edit(reqBook.getId(),reqBook)).thenReturn(reqBook);
        when(bookService.findById(reqBook.getId())).thenReturn(reqBook);
        when(bookService.findByTitleAndAuthor(reqBook.getTitle(),reqBook.getAuthor())).thenReturn(reqBook);

        ResultActions result2 = performPatchWithRequestBodyAndPathVariables(BOOKS+BOOK_ID_PATH, reqBook,reqBook.getId());
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book edited successfully")));
    }

    @Test
    void delete() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(10)
                .price(19.99)
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        doNothing().when(bookService).delete(reqBook.getId());

        ResultActions result2 = performDeleteWithPathVariable(BOOKS+BOOK_ID_PATH, reqBook.getId());
        result2.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("Book deleted successfully")));
        verify(bookService, times(1)).delete(reqBook.getId());
    }

    @Test
    void sell() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(10)
                .price(19.99)
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);
        when(bookService.sell(reqBook.getId())).thenReturn(reqBook);
        when(bookService.findById(reqBook.getId())).thenReturn(reqBook);

        ResultActions result2 = performPostWithRequestBodyAndPathVariables(BOOKS+BOOK_ID_PATH, reqBook,reqBook.getId());
        result2.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("Book sold successfully")));
    }
}
