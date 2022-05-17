package com.raulp.report;

import com.raulp.book.model.dto.BookDTO;
import com.raulp.UrlMapping;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.raulp.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {

    public static String docPath = UrlMapping.REPORT_LOCAL_PATH + UrlMapping.REPORT_LOCAL_CSV;
    @Override
    public String export(List<BookDTO> books) {

        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[]
                { "Title", "Author", "Genre", "Description", "Price" });

        for (int i = 0; i < books.size(); i++) {
            dataLines.add(new String[]{
                    books.get(i).getTitle(),
                    books.get(i).getAuthor(),
                    books.get(i).getGenre() != null ? books.get(i).getGenre().toString() : null,
                    books.get(i).getDescription(),
                    books.get(i).getPrice() != null ? books.get(i).getPrice().toString() : null,
            });
        }

        File csvOutputFile = new File(docPath);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return docPath;
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
