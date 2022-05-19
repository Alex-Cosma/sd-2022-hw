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
        Integer countItems = 0;
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try{
                PDPageContentStream content = new PDPageContentStream(doc, page);

                content.beginText();
                content.setFont(PDType1Font.TIMES_ROMAN, 12);
                content.setLeading(14.5f);
                content.newLineAtOffset(25, 750);

                List<BookDTO> items = reportService.findItemsByQuantityEquals(0);

                if (!items.isEmpty()) {

                    content.showText("Books out of stock:");
                    content.newLine();

                    Field[] fields = BookDTO.class.getDeclaredFields();
                    StringBuilder sb = new StringBuilder();
                    for (Field field : fields) {
                        sb.append(field.getName()).append(" ");
                    }

                    countItems += 2;
                    content.showText(sb.toString());
                    content.newLine();

                    for (BookDTO item : items) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(item.getId())
                                .append(" ")
                                .append(item.getTitle())
                                .append(" ")
                                .append(item.getAuthor())
                                .append(" ")
                                .append(item.getGenre())
                                .append(" ")
                                .append(item.getPrice());

                        content.showText(stringBuilder.toString());
                        content.newLine();
                        countItems++;

                        // if page is full, create a new page in the document
                        // not professional implementation tho
                        if(countItems >= 50){
                            countItems = 0;
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
                    content.showText("No items are out of stock");
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

