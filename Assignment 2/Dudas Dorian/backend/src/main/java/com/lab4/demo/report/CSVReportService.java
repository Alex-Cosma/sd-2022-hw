package com.lab4.demo.report;

import com.lab4.demo.book.model.dto.BookDTO;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.lab4.demo.report.ReportType.CSV;
import static java.util.Optional.ofNullable;

@Service
public class CSVReportService implements ReportService {
    @Override
    public String export(List<BookDTO> books) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'_'HH-mm");
        Date date = new Date(System.currentTimeMillis());
        File file = new File("../report_" + formatter.format(date) + ".csv");
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);

            String[] header = { "ID", "Title", "Author", "Genre", "Price", "Quantity" };
            writer.writeNext(header);

            for (BookDTO book : books) {
                String[] data = {
                        book.getId().toString(),
                        book.getTitle(),
                        ofNullable(book.getAuthor()).orElse(""),
                        ofNullable(book.getGenre()).orElse(""),
                        ofNullable(book.getPrice()).map(Object::toString).orElse(""),
                        book.getQuantity().toString()
                };
                writer.writeNext(data);
            }

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
