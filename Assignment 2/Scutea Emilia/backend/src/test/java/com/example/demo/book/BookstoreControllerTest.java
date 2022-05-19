package com.example.demo.book;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.report.ReportService;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.storage.StorageService;
import org.apiguardian.api.API;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.randomLong;
import static com.example.demo.TestCreationFactory.randomString;
import static com.example.demo.UrlMapping.*;
import static com.example.demo.report.ReportType.CSV;
import static com.example.demo.report.ReportType.PDF;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookstoreControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookstoreController controller;

    @Mock
    private BookstoreService bookstoreService;

    @Mock
    private StorageService storageService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookstoreController(bookstoreService, storageService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(BookDTO.class);
        when(bookstoreService.findAll()).thenReturn(books);

        ResultActions response = performGet(API_PATH + BOOKS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }

//

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder().title(randomString())
                .author(randomString())
                .build();

        when(bookstoreService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(API_PATH + BOOKS_CREATE, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .build();

        when(bookstoreService.edit(id, reqBook)).thenReturn(reqBook);

        ResultActions result = performPutWithRequestBodyAndPathVariables(API_PATH + BOOKS_ID_EDIT, reqBook, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    public void delete() throws Exception {
        long id = randomLong();
        doNothing().when(bookstoreService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(API_PATH + BOOKS_ID_DELETE, id);

        result.andExpect(status().isOk());
    }


    @Test
    void exportReport() throws Exception {
//        final String csv = "src/main/resources/csvReport.csv";
//        final String pdf = "src/main/resources/pdfReport.pdf";
//        when(bookstoreService.export(CSV)).thenReturn(csv);
//        when(bookstoreService.export(PDF)).thenReturn(pdf);
////
//        ResultActions responseCsv = performGetWithPathVariables(BOOKS + EXPORT_REPORT, CSV.name());
//        ResultActions responsePdf = performGetWithPathVariables(BOOKS + EXPORT_REPORT, PDF.name());
////
////        Assertions.assertEquals(HttpStatus.OK, responseCsv.getStatusCode());
////
////        responseCsv.andExpect(status().isOk());
////        responsePdf.andExpect(status().isOk());
//
//        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
////        Assertions.assertEquals(HttpStatus.OK, csvReportService.export().());
//
//        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
//        Assertions.assertEquals(HttpStatus.OK, responseCsv.andExpect(status().isOk()));
    }

    @Test
    void allBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookstoreService.findAll()).thenReturn(books);

        ResultActions response = performGet(API_PATH + BOOKSTORE);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void searchBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        String str = "1";
        when(bookstoreService.searchItems(str)).thenReturn(books);

        ResultActions response = performGetWithPathVariables(API_PATH + BOOKSTORE_SEARCH_BOOKS, str);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }

    @Test
    void sellBook() throws Exception {
        final long id = randomLong();
        BookDTO reqItem = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .quantity(20)
                .build();

        BookDTO sellItem = reqItem;
        sellItem.setQuantity(sellItem.getQuantity() - 1);

        when(bookstoreService.decreaseBookQuantity(id, reqItem)).thenReturn(true);

        ResultActions result = performPutWithRequestBodyAndPathVariables(API_PATH + BOOKSTORE_ID_SELL, reqItem, id);
        result.andExpect(status().isOk());

    }
}