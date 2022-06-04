package com.rdaniel.sd.a2.backend.book;

import com.rdaniel.sd.a2.backend.book.dto.BookDto;
import com.rdaniel.sd.a2.backend.book.dto.BookFilterRequestDto;
import com.rdaniel.sd.a2.backend.report.ReportServiceFactory;
import com.rdaniel.sd.a2.backend.report.ReportType;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;

import static com.rdaniel.sd.a2.backend.UrlMappings.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(BOOKS_PATH)
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;
  private final ReportServiceFactory reportServiceFactory;

  @GetMapping
  @ResponseStatus(OK)
  public List<BookDto> getAllBooks() {
    return bookService.findAll();
  }

  @GetMapping(FILTERED_PATH)
  @ResponseStatus(OK)
  public Page<BookDto> filteredItems(@ModelAttribute("filter") BookFilterRequestDto filter,
                                     @PageableDefault(sort = {"author"}) Pageable pageable) {
    return bookService.findAllFiltered(filter, pageable);
  }

  @GetMapping(RESOURCE_BY_ID)
  @ResponseStatus(OK)
  public BookDto getBook(@PathVariable Long id) {
    try {
      return bookService.findById(id);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @GetMapping(EXPORT_REPORT)
  @ResponseStatus(OK)
  public String exportReport(@PathVariable ReportType type) {
    try {
      return reportServiceFactory.getReportService(type).export();
    } catch (FileNotFoundException | JRException e) {
      throw new RuntimeException(e);
    }
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public BookDto createBook(@RequestBody @Valid BookDto bookDto) {
    try {
      return bookService.save(bookDto);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @PatchMapping(RESOURCE_BY_ID)
  @ResponseStatus(OK)
  public BookDto sellBooks(@PathVariable Long id, @RequestBody int quantityToSell) {
    try {
      return bookService.sellBooks(id, quantityToSell);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @PutMapping(RESOURCE_BY_ID)
  @ResponseStatus(OK)
  public BookDto updateBook(@PathVariable("id") Long id, @RequestBody @Valid BookDto bookDto) {
    try {
      return bookService.update(id, bookDto);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @DeleteMapping(RESOURCE_BY_ID)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBook(@PathVariable("id") Long id) {
    try {
      bookService.delete(id);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
