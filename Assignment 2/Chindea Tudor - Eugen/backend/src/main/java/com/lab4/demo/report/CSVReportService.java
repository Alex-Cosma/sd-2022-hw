package com.lab4.demo.report;
import com.opencsv.CSVWriter;
import com.lab4.demo.book.model.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {
    @Override
    public String export(List<BookDTO> books) {
        File csvOutputFile = new File("report.csv");
        try {
            FileWriter outputfile = new FileWriter(csvOutputFile);
            CSVWriter writer = new CSVWriter(outputfile);
            String[] header = { "Title", "Author"};
            writer.writeNext(header);
            for(BookDTO book: books){
                if(book.getQuantity() == 0){
                    String[] data = {book.getTitle(), book.getAuthor()};
                    writer.writeNext(data);
                }

            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
