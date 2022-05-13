package com.example.bookstore.Report;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.example.bookstore.Report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService{
    @Override
    public String generateReport(List<BookDTO> books) {
        PDDocument doc = new PDDocument();
        PDPage pdPage = new PDPage();
        StringBuilder sb = new StringBuilder();
        for(BookDTO book : books){
            sb.append(book.getTitle());
        }
        doc.addPage(pdPage);
        try {
            PDPageContentStream content = new PDPageContentStream(doc, pdPage);
            content.beginText();
            content.setFont(PDType1Font.COURIER,26);
            content.moveTextPositionByAmount(250,750);
            content.drawString(sb.toString());
            content.endText();
            content.close();
            doc.save("C:\\Users\\Alex\\Desktop\\report.pdf");
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "PDF";
    }

    @Override
    public ReportType getType() {
        return PDF;
    }
}
