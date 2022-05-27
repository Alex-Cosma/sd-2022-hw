package com.example.demo.report;

import com.example.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static com.example.demo.report.ReportType.PDF;

@Service
@RequiredArgsConstructor
public class PDFReportService implements ReportService {
    private final ReportServiceImpl reportService;

    @Override
    public String export() {
        String filename = "src/main/resources/pdfReport.pdf";
        Integer countBooks = 0;
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try{
                PDPageContentStream content = new PDPageContentStream(doc, page);

                content.beginText();
                content.setFont(PDType1Font.TIMES_ROMAN, 12);
                content.setLeading(14.5f);
                content.newLineAtOffset(25, 750);

                List<BookDTO> books = reportService.findBooksByQuantityEquals(0);

                if (!books.isEmpty()) {

                    content.showText("Books out of stock:");
                    content.newLine();

                    Field[] fields = BookDTO.class.getDeclaredFields();
                    StringBuilder sb = new StringBuilder();
                    for (Field field : fields) {
                        sb.append(field.getName()).append(" ");
                    }

                    countBooks += 2;
                    content.showText(sb.toString());
                    content.newLine();

                    for (BookDTO book : books) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(book.getId())
                                .append(" ")
                                .append(book.getTitle())
                                .append(" ")
                                .append(book.getAuthor())
                                .append(" ")
                                .append(book.getGenre())
                                .append(" ")
                                .append(book.getPrice());

                        content.showText(stringBuilder.toString());
                        content.newLine();
                        countBooks++;

                        // if page is full, create a new page in the document
                        // not professional implementation tho
                        if(countBooks >= 50){
                            countBooks = 0;
                            content.endText();
                            content.close();

                            PDPage newPage = new PDPage();
                            doc.addPage(newPage);
                            content = new PDPageContentStream(doc, newPage);
                            content.beginText();
                            content.setFont(PDType1Font.TIMES_ROMAN, 12);
                            content.setLeading(14.5f);
                            content.newLineAtOffset(25, 750);
                        }
                    }
                } else {
                    content.showText("No books are out of stock");
                }

                content.endText();
                content.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            doc.save(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}

