package com.lab4.demo.report;

import com.lab4.demo.item.model.dto.ItemDTO;
import org.springframework.stereotype.Service;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {
    @Override
    public String export() {
        return "I am a CSV reporter.";
    }

    public String exportC(List<ItemDTO> list)  {
        File file = new File("report.csv");
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            String[] header = { "Title", "Author", "Genre", "Quantity", "Price"};
            writer.writeNext(header);
            for(ItemDTO item: list){
                String[] data = {item.getTitle(), item.getAuthor(), item.getGenre(),Integer.toString(item.getQuantity()),Integer.toString(item.getPrice())};
                writer.writeNext(data);
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "I am a CSV reporter";
    }
    @Override
    public ReportType getType() {
        return CSV;
    }
}
