package com.lab4.demo.report;

import com.lab4.demo.book.BookService;
import com.lab4.demo.book.model.dto.BookDTO;
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
public class PdfReportService implements ReportService
{

    private BookService bookService;

    public PdfReportService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String export(List<BookDTO> books) throws IOException{
        PDDocument doc = null;
        try
        {
            doc = new PDDocument();

            PDPage page = new PDPage();
            doc.addPage( page );


            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
            for(BookDTO book : books ){
                if(book.getQuantity() == 0){
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                    contentStream.newLineAtOffset(200,685);
                    contentStream.showText(book.getTitle());
                    contentStream.endText();
                }
            }

            contentStream.close();
            doc.save( "report.pdf" );
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
