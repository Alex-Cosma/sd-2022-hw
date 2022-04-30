package com.example.assignment2.reports;

import com.example.assignment2.bookstore.model.dto.BookDTO;
import com.example.assignment2.bookstore.BookRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.example.assignment2.reports.ReportType.PDF;

@Service
public class ReportPdfService implements ReportService {
    BookRepository bookRepository;

    @Override
    public String export(List<BookDTO> bookList) throws IOException {
        StringBuilder sb = new StringBuilder();
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc,page);
        contentStream.setFont(PDType1Font.TIMES_ROMAN,12);
        contentStream.beginText();
        contentStream.newLineAtOffset(20,720);
        for(BookDTO book: bookList){
            if(book.getQuantity()==0) {
                sb.append(book.getTitle()+", ");
            }
        }
        contentStream.showText(sb.toString());
        contentStream.endText();
        contentStream.close();
        doc.save(new FileOutputStream("pdfBooks.pdf"));
        System.out.println("Saved");
        return "pdf";
    }

    @Override
    public ReportType getType() {
        return PDF;
    }
}
