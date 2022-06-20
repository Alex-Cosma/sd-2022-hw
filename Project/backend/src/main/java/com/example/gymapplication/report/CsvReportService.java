package com.example.gymapplication.report;

import com.example.gymapplication.training.model.dto.TrainingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvReportService implements ReportService {

    @Override
    public String export(List<TrainingDTO> trainings, String username) {
        try (PrintWriter writer = new PrintWriter("report.csv")) {
            writer.write("Hello " + username + "!\n");
            if(!trainings.isEmpty()) {
                writer.write("You are enrolled in the following trainings:\n");
                for (TrainingDTO item : trainings) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(item.getTitle());
                    sb.append(", ");
                    sb.append(item.getType());
                    sb.append(", ");
                    sb.append(item.getDate());
                    sb.append(", ");
                    sb.append(item.getLocation());
                    sb.append("\n");

                    writer.write(sb.toString());
                }
            }else {
                writer.write("You are not registered for any training\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        File file = new File("report.csv");
        Resource resource = null;
        try {
            resource = new UrlResource(file.toPath().toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource.getFile().getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return ReportType.CSV;
    }
}
