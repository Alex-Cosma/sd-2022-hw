package com.lab4.demo.report;

import com.lab4.demo.book.model.dto.BookDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;
import static java.util.Optional.ofNullable;

@Service
public class PdfReportService implements ReportService {
    @Override
    public String export(List<BookDTO> books) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDFont font = PDType1Font.HELVETICA;

        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(font, 20);
            contentStream.newLineAtOffset(10, 700);
            contentStream.setLeading(14.5f);

            contentStream.showText("Books out of stock:");
            contentStream.newLine();
            contentStream.setFont(font, 12);
            contentStream.showText("===============================================================================================");
            contentStream.newLine();
            contentStream.showText("ID | Title | Author | Genre | Price | Quantity");
            contentStream.newLine();

            for (BookDTO book : books) {
                contentStream.showText(book.getId().toString() + " ");
                contentStream.showText(book.getTitle() + " ");
                contentStream.showText(ofNullable(book.getAuthor()).orElse("--------- "));
                contentStream.showText(ofNullable(book.getGenre()).orElse("--------- "));
                contentStream.showText(ofNullable(book.getPrice()).map(Object::toString).orElse("--------- "));
                contentStream.showText(book.getQuantity().toString() + " ");
                contentStream.newLine();
            }
            contentStream.endText();
            contentStream.close();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'_'HH-mm");
            Date date = new Date(System.currentTimeMillis());
            document.save("../report_" + formatter.format(date) + ".pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return "I am a PDF reporter.";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
