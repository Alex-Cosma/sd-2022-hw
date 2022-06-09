package com.rdaniel.sd.a2.backend.report;

import com.rdaniel.sd.a2.backend.book.BookRepository;
import com.rdaniel.sd.a2.backend.book.model.Book;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public abstract class ReportService {
  private final BookRepository bookRepository;

  protected ReportService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> getAllBooksOutOfStock() {
    return bookRepository.findAllByQuantity(0);
  }

  public abstract String export() throws FileNotFoundException, JRException;

  public abstract ReportType getReportType();
}
