package com.lab4.demo.report;

import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.lab4.demo.report.ReportType.PDF;

@Service
@RequiredArgsConstructor
public class PdfReportService implements ReportService {

    private  ItemService itemService;

    public PdfReportService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public String export() throws IOException{
        PDDocument doc = null;
        try
        {
            doc = new PDDocument();

            PDPage page = new PDPage();
            doc.addPage( page );


            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
            for(ItemDTO item : itemService.findAll() ){
                if(item.getQuantity() == 0){
                    contentStream.beginText();
                    contentStream.newLineAtOffset(200,685);
                    contentStream.showText(item.getTitle());
                    contentStream.endText();
                }
            }

            contentStream.close();
            doc.save( "C:\\Users\\tudor\\Downloads\\report.pdf" );
        }
        finally
        {
            if( doc != null )
            {
                doc.close();
            }
        }
                    return "I am a PDF reporter.";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
