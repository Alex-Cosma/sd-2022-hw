package com.rdaniel.sd.a2.backend.report;

import com.rdaniel.sd.a2.backend.book.BookRepository;
import com.rdaniel.sd.a2.backend.book.model.Book;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CsvReportService extends ReportService {

  protected CsvReportService(BookRepository bookRepository) {
    super(bookRepository);
  }

  @Override
  public String export() throws FileNotFoundException, JRException {
    final String path = "books_out_of_stock.csv";
    final List<Book> booksOutOfStock = getAllBooksOutOfStock();
    final List<String[]> dataLines = new ArrayList<>();
    dataLines.add(new String[]{"Id", "Title", "Author", "Genre", "Quantity", "Price"});
    booksOutOfStock.forEach(book -> {
      String[] dataLine = new String[6];
      dataLine[0] = book.getTitle();
      dataLine[1] = book.getAuthor();
      dataLine[3] = book.getGenre();
      dataLine[4] = String.valueOf(book.getQuantity());
      dataLine[5] = String.valueOf(book.getPrice());
      dataLines.add(dataLine);
    });
    File file = new File(path);
    try (PrintWriter pw = new PrintWriter(file)) {
      dataLines.stream()
          .map(this::convertToCSV)
          .forEach(pw::println);
    }
    return "Report generated in path " + path;
  }

  private String convertToCSV(String[] data) {
    return Stream.of(data)
        .map(this::escapeSpecialCharacters)
        .collect(Collectors.joining(","));
  }

  private String escapeSpecialCharacters(String data) {
    String escapedData = data.replaceAll("\\R", " ");
    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
      data = data.replace("\"", "\"\"");
      escapedData = "\"" + data + "\"";
    }
    return escapedData;
  }

  @Override
  public ReportType getReportType() {
    return ReportType.CSV;
  }
}
