package com.policeSection.Reports;


import com.policeSection.Reports.Model.Report;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportsRepository reportsRepository;

    public Report create(Report r)
    {
        return reportsRepository.save(r);
    }
    public List<Report> allReports()
    {
        return reportsRepository.findAll();
    }

    public ByteArrayInputStream exportP() {
        List<Report> reports=allReports();
        try (PDDocument doc = new PDDocument()) {

            PDPage myPage = new PDPage();
            doc.addPage(myPage);

            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {

                cont.beginText();
                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
                cont.setLeading(14.5f);
                cont.newLineAtOffset(25, 700);

                cont.showText("Section ");
                cont.newLine();
                cont.showText(" Description ");
                cont.newLine();



                for(Report r: reports){
                    cont.showText(Integer.toString(r.getSectionNumber())+"     ");
                    cont.showText(r.getDescription());
                    cont.newLine();
                }
                cont.endText();
                cont.close(); // do this before saving!
                doc.save("pdfReport.pdf");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                doc.save(byteArrayOutputStream);
                doc.close();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                return inputStream;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream2=new ByteArrayOutputStream();
        return new ByteArrayInputStream(byteArrayOutputStream2.toByteArray());
    }

}
