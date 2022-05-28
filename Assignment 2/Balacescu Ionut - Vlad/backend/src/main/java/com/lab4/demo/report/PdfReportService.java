package com.lab4.demo.report;

import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {

    @Override
    public void export(List<Item> items) throws IOException {

        File file = new File("C:\\Users\\Vlad\\Desktop\\Uni Work\\SEM2\\SD\\LabCodeExamples\\Example 7 - Spring #4 (+ more frontend)\\backend\\Quantity0.pdf");
        PDDocument document = PDDocument.load(file);
        PDPage blankPage = new PDPage();
        document.addPage( blankPage );
        PDPage page = document.getPage(0);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(25, 725);

        if(items.size()==0){
            contentStream.showText("There are no books with quantity 0");
        }else {
            for(Item itm : items){

                contentStream.showText(itm.getTitle() + " "+ itm.getAuthor());
                contentStream.newLine();
            }
        }
        contentStream.endText();
        contentStream.close();
        document.save(new File("C:\\Users\\Vlad\\Desktop\\Uni Work\\SEM2\\SD\\LabCodeExamples\\Example 7 - Spring #4 (+ more frontend)\\backend\\Quantity0.pdf"));
        document.close();
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
