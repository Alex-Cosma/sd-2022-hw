package com.assignment2.bookstoreapp.report;


import com.assignment2.bookstoreapp.report.dto.ReportDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static com.assignment2.bookstoreapp.report.ReportType.PDF;

@Service
public class PDFReportService implements ReportService {

    @Override
    public <T> boolean export(ReportDTO<T> reportDTO) {
        try {

            int xOffset = 50;
            int yOffSet = 20;

            ArrayList<String> convertedData = convertListToExportData(reportDTO);
            Collections.reverse(convertedData);

            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);

            for(String s: convertedData) {
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(xOffset, yOffSet);
                yOffSet += 20;
                content.showText(s);
                content.endText();
            }

            content.close();

            doc.save(getReportTitle());
            doc.close();

            System.out.println("your file created in : "+ System.getProperty("user.dir"));

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ReportType getType() {
        return PDF;
    }

    public <T> ArrayList<String> convertListToExportData(ReportDTO<T> reportDTO) {

        ArrayList<String> lines = new ArrayList<>();
        lines.add(reportDTO.getTitle());

        try {
            for(T obj: reportDTO.getData()){
                for(Field field: obj.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    lines.add(field.getName() + ": " + field.get(obj));
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        lines.add("Report created on " + LocalDateTime.now());

        return lines;
    }

    private String getReportTitle(){
        return "report.pdf"; //("Report" + LocalDateTime.now() + ".pdf").replace(':','-');
    }
}
