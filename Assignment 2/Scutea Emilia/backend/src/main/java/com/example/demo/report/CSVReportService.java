package com.example.demo.report;


import com.example.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static com.example.demo.report.ReportType.CSV;

@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService {

    private final ReportServiceImpl reportService;

    @Override
    public String export() {
        String filename = "src/main/resources/csvReport.csv";
        try(PrintWriter writer = new PrintWriter(filename)){
            List<BookDTO> items = reportService.findItemsByQuantityEquals(0);

            if(!items.isEmpty()){
                for(BookDTO item : items){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder
                            .append("id ")
                            .append(item.getId())
                            .append(", title ")
                            .append(item.getTitle())
                            .append(", genre ")
                            .append(item.getGenre())
                            .append(", price")
                            .append(item.getPrice());

                    writer.println(stringBuilder.toString());
                }
            }


        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return filename;
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
