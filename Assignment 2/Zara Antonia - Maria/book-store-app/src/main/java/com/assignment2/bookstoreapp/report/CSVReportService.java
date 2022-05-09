package com.assignment2.bookstoreapp.report;

import com.assignment2.bookstoreapp.report.dto.ReportDTO;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static com.assignment2.bookstoreapp.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {
    @Override
    public <T> boolean export(ReportDTO<T> reportDTO) {

        File file = new File("report.csv");
        try {
            FileOutputStream outputFile = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(outputFile, "UTF-8");
            CSVWriter writer = new CSVWriter(osw);

            ArrayList<String[]> data = convertListToExportData(reportDTO);

            writer.writeAll(data);
            writer.close();
            osw.close();
            outputFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public ReportType getType() {
        return CSV;
    }

    public <T> ArrayList<String[]> convertListToExportData(ReportDTO<T> reportDTO) {

        T t = reportDTO.getData().get(0);

        ArrayList<String[]> rows = new ArrayList<>();

        int noFields = t.getClass().getDeclaredFields().length;

        String[] header = new String[noFields];
        int index = 0;
        for(Field field: t.getClass().getDeclaredFields()){
            header[index++] = field.getName();
        }

        rows.add(header);

        for(T obj: reportDTO.getData()){
            index = 0;
            String[] row = new String[noFields];
            for(Field field: obj.getClass().getDeclaredFields()){
                field.setAccessible(true);

                try {
                    row[index++] = field.get(obj).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            rows.add(row);
        }

        return rows;
    }

}
