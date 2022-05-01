package com.lab4.demo.report;

import com.lab4.demo.frontoffice.BookService;
import com.lab4.demo.frontoffice.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;

@Service
@RequiredArgsConstructor
public class PDFReportService implements ReportService {

    private final BookService bookService;

    @Override
    public String export() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        List<BookDTO> booksOutOfStock = bookService.findAllOutOfStock();

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.COURIER, 12);
        contentStream.beginText();

        contentStream.newLineAtOffset(25, 700);
        for (BookDTO book : booksOutOfStock) {
            contentStream.newLine();
            contentStream.showText(book.getTitle());
            contentStream.newLine();
            // contentStream.newLineAtOffset(25, 700 + (booksOutOfStock.indexOf(book) * 2));
        }
        contentStream.endText();
        contentStream.close();

        document.save("pdf-report.pdf");
        document.close();

        return "Exported to pdf-report.pdf";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
