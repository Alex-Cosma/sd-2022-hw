package com.example.bookstore.report;

import com.example.bookstore.book.BookService;
import com.example.bookstore.book.dto.BookDTO;
import com.example.bookstore.book.model.Book;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.example.bookstore.report.ReportType.PDF;

@Service
@RequiredArgsConstructor
public class PDFReportService implements ReportService {

    private final BookService bookService;

    @Override
    public String export() {
        List<BookDTO> books=bookService.findAllOutOfStock();
       try{
           PDDocument document = new PDDocument();
           PDPage page = new PDPage();
           document.addPage(page);
           PDFont font = PDType1Font.HELVETICA;
           PDPageContentStream content = new PDPageContentStream(document, page);
           content.setFont(font, 12);
           for(BookDTO book: books){
               content.beginText();
               content.newLineAtOffset(100, 700 - 20f * books.indexOf(book));
               content.showText(book.getTitle()+" - "+book.getAuthor());
               content.endText();
           }
           content.close();
           document.save("OutOfStockBooks.pdf");
           document.close();
           return "PDF Report!";
       }catch(IOException e){
           e.printStackTrace();
       }
       return null;
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
