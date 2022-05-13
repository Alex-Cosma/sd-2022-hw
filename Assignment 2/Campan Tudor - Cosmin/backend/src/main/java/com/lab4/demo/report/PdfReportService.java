package com.lab4.demo.report;

import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.dto.ItemDTO;
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
public class PdfReportService implements ReportService {

    public String exportP(List<ItemDTO> list) {
        try (PDDocument doc = new PDDocument()) {

            PDPage myPage = new PDPage();
            doc.addPage(myPage);

            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {

                cont.beginText();
                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
                cont.setLeading(14.5f);
                cont.newLineAtOffset(25, 700);

                cont.showText("Books out of stock");
                cont.newLine();
                cont.showText(" Title ");
                cont.newLine();



                for(ItemDTO item: list){
                    cont.showText(item.getTitle());
                    cont.newLine();
                }
                cont.endText();
            }
            doc.save("pdfReport.pdf");
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }

        return "I am a PDF reporter";
    }


    @Override
    public String export() throws IOException {
        return null;
    }

    @Override
    public ReportType getType() {
        return PDF;
    }


}
