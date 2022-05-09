package com.example.bookstore.report;

import com.example.bookstore.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfReportService implements ReportService{

    @Override
    public String export(List<BookDTO> books) {
        try (PDDocument doc = new PDDocument()) {
            PDPage myPage = new PDPage();
            doc.addPage(myPage);

            try (PDPageContentStream content = new PDPageContentStream(doc, myPage)) {
                content.beginText();
                content.setFont(PDType1Font.TIMES_ROMAN, 12);
                content.setLeading(14.5f);
                content.newLineAtOffset(20, 700);

                if(!books.isEmpty()) {
                    for (BookDTO item : books) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(item.getTitle());
                        sb.append(", ");
                        sb.append(item.getAuthor());
                        sb.append(", ");
                        sb.append(item.getGenre());
                        sb.append(", ");
                        sb.append(item.getPrice());

                        content.showText(sb.toString());
                        content.newLine();
                    }
                }
                content.endText();
            }
            doc.save("report.pdf");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return "I am a PDF reporter.";
    }

    @Override
    public ReportType getType() {
        return ReportType.PDF;
    }
}
