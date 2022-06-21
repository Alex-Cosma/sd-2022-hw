package com.rdaniel.sd.a2.backend.report;

import com.rdaniel.sd.a2.backend.book.BookRepository;
import com.rdaniel.sd.a2.backend.book.model.Book;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public class PdfReportService extends ReportService {

  protected PdfReportService(BookRepository bookRepository) {
    super(bookRepository);
  }

  @Override
  public String export() throws FileNotFoundException, JRException {
    final String path = "books_out_of_stock.pdf";
    final List<Book> booksOutOfStock = getAllBooksOutOfStock();
    final File file = ResourceUtils.getFile("classpath:books_out_of_stock_pdf.jrxml");
    JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(booksOutOfStock);
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
    JasperExportManager.exportReportToPdfFile(jasperPrint, path);
    return "Report generated in path " + path;
  }

  @Override
  public ReportType getReportType() {
    return ReportType.PDF;
  }
}
