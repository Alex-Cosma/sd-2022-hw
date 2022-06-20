package com.example.gymapplication.report;

import com.example.gymapplication.training.model.dto.TrainingDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfReportService implements ReportService{

    @Override
    public String export(List<TrainingDTO> trainings, String username) {
        try (PDDocument doc = new PDDocument()) {
            PDPage myPage = new PDPage();
            doc.addPage(myPage);

            try (PDPageContentStream content = new PDPageContentStream(doc, myPage)) {
                content.beginText();
                content.setFont(PDType1Font.TIMES_ROMAN, 12);
                content.setLeading(14.5f);
                content.newLineAtOffset(20, 700);

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Hello " + username + "!");

                content.showText(stringBuilder.toString());
                content.newLine();

                if(!trainings.isEmpty()) {
                    content.showText("You are enrolled in the following trainings:");
                    content.newLine();
                    for (TrainingDTO item : trainings) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(item.getTitle());
                        sb.append(", ");
                        sb.append(item.getType());
                        sb.append(", ");
                        sb.append(item.getDate());
                        sb.append(", ");
                        sb.append(item.getLocation());

                        content.showText(sb.toString());
                        content.newLine();
                    }
                }
                else{
                    content.showText("You are not registered for any training");
                    content.newLine();
                }
                content.endText();
            }
            doc.save("report.pdf");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        File file = new File("report.pdf");
        Resource resource = null;
        try {
            resource = new UrlResource(file.toPath().toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource.getFile().getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "I am a PDF reporter.";
    }

    @Override
    public ReportType getType() {
        return ReportType.PDF;
    }
}
