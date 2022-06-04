package com.rdaniel.sd.a2.backend.book;

import com.rdaniel.sd.a2.backend.BaseControllerTest;
import com.rdaniel.sd.a2.backend.book.dto.BookDto;
import com.rdaniel.sd.a2.backend.book.dto.BookFilterRequestDto;
import com.rdaniel.sd.a2.backend.report.ReportService;
import com.rdaniel.sd.a2.backend.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.rdaniel.sd.a2.backend.TestCreationFactory.*;
import static com.rdaniel.sd.a2.backend.UrlMappings.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

  @InjectMocks
  private BookController bookController;

  @Mock
  private BookService bookService;

  @Mock
  private ReportServiceFactory reportServiceFactory;


  @BeforeEach
  protected void setUp() {
    super.setUp();
    bookController = new BookController(bookService, reportServiceFactory);
    mockMvc = MockMvcBuilders.standaloneSetup(bookController)
        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        .build();
  }

  @Test
  void getAllBooks_returnedList_status200() throws Exception {
    final List<BookDto> books = listOf(BookDto.class, 10);
    when(bookService.findAll()).thenReturn(books);

    mockMvc.perform(get(BOOKS_PATH))
        .andExpect(status().isOk())
        .andExpect(jsonContentToBe(books));

    verify(bookService, times(1)).findAll();
  }

  @Test
  void filteredItems() throws Exception {
    final BookFilterRequestDto filters = BookFilterRequestDto.builder()
        .author("author")
        .title("title")
        .price(1.0)
        .build();

    final Sort complex = Sort.by(ASC, "author").and(Sort.by(ASC, "title")).and(Sort.by(ASC, "genre"));
    final PageRequest pagination = PageRequest.of(1, 10, complex);
    final Page<BookDto> page = new PageImpl<>(listOf(BookDto.class, 15), pagination, 15);
    when(bookService.findAllFiltered(any(BookFilterRequestDto.class), any(PageRequest.class))).thenReturn(page);

    ResultActions result = performGetWithModelAttributeAndParams(BOOKS_PATH + FILTERED_PATH, Pair.of("filter", filters), pairsFromPagination(pagination));
    result.andExpect(status().isOk())
        .andExpect(jsonContentToBe(page));

  }

  @Test
  void getBook_validIdentifier_status200() throws Exception {
    final BookDto bookDto = newBookDto();
    when(bookService.findById(any(Long.class))).thenReturn(bookDto);

    ResultActions response = performGetWithPathVariable(BOOKS_PATH + RESOURCE_BY_ID, 1L);
    response.andExpect(status().isOk())
        .andExpect(jsonContentToBe(bookDto));

    verify(bookService, times(1)).findById(any(Long.class));
  }

  @Test
  void getBook_invalidIdentifier_status404() throws Exception {
    when(bookService.findById(any(Long.class))).thenThrow(EntityNotFoundException.class);

    ResultActions response = performGetWithPathVariable(BOOKS_PATH + RESOURCE_BY_ID, 1L);
    response.andExpect(status().isNotFound());

    verify(bookService, times(1)).findById(any(Long.class));
  }

  @Test
  void createBook_validData_status201() throws Exception {
    final BookDto bookDto = newBookDto();
    when(bookService.save(any(BookDto.class))).thenReturn(bookDto);

    ResultActions response = performPostWithRequestBody(BOOKS_PATH, bookDto);
    response.andExpect(status().isCreated())
        .andExpect(jsonContentToBe(bookDto));

    verify(bookService, times(1)).save(any(BookDto.class));
  }

  @Test
  void createBook_invalidData_status400() throws Exception {
    final BookDto bookDto = newBookDto();
    bookDto.setTitle("");

    ResultActions response = performPostWithRequestBody(BOOKS_PATH, bookDto);
    response.andExpect(status().isBadRequest());

    verify(bookService, never()).save(any(BookDto.class));
  }


  @Test
  void sellBooks_validIdentifier_status200() throws Exception {
    final BookDto bookDto = newBookDto();
    when(bookService.sellBooks(any(Long.class), any(Integer.class))).thenReturn(bookDto);

    ResultActions response = performPatchWithRequestBodyAndPathVariable(BOOKS_PATH + RESOURCE_BY_ID, 1, 1L);

    response.andExpect(status().isOk())
        .andExpect(jsonContentToBe(bookDto));

    verify(bookService, times(1)).sellBooks(any(Long.class), any(Integer.class));
  }

  @Test
  void updateBook_validIdentifierAndContent_status200() throws Exception {
    final BookDto bookDto = newBookDto();
    when(bookService.update(any(Long.class), any(BookDto.class))).thenReturn(bookDto);

    final ResultActions resultActions = performPutWithRequestBodyAndPathVariable(BOOKS_PATH + RESOURCE_BY_ID, bookDto, 1L);
    resultActions.andExpect(status().isOk())
        .andExpect(jsonContentToBe(bookDto));

    verify(bookService, times(1)).update(any(Long.class), any(BookDto.class));
  }

  @Test
  void updateBook_invalidIdentifier_status404() throws Exception {
    final BookDto bookDto = newBookDto();
    when(bookService.update(any(Long.class), any(BookDto.class))).thenThrow(EntityNotFoundException.class);

    final ResultActions resultActions = performPutWithRequestBodyAndPathVariable(BOOKS_PATH + RESOURCE_BY_ID, bookDto, 1L);
    resultActions.andExpect(status().isNotFound());

    verify(bookService, times(1)).update(any(Long.class), any(BookDto.class));
  }

  @Test
  void updateBook_validIdentifierButInvalidContent_status400() throws Exception {
    final BookDto bookDto = newBookDto();
    bookDto.setTitle("");

    final ResultActions resultActions = performPutWithRequestBodyAndPathVariable(BOOKS_PATH + RESOURCE_BY_ID, bookDto, 1L);
    resultActions.andExpect(status().isBadRequest());

    verify(bookService, times(0)).update(any(Long.class), any(BookDto.class));
  }


  @Test
  void deleteBook_validIdentifier_status204() throws Exception {
    final ResultActions resultActions = performDeleteWithPathVariable(BOOKS_PATH + RESOURCE_BY_ID, 1L);
    resultActions.andExpect(status().isNoContent());

    verify(bookService, times(1)).delete(any(Long.class));
  }

  @Test
  void deleteBook_invalidIdentifier_status404() throws Exception {
    doThrow(EntityNotFoundException.class).when(bookService).delete(any(Long.class));

    final ResultActions resultActions = performDeleteWithPathVariable(BOOKS_PATH + RESOURCE_BY_ID, 1L);
    resultActions.andExpect(status().isNotFound());

    verify(bookService, times(1)).delete(any(Long.class));
  }
}