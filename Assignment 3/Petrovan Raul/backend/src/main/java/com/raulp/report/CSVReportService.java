package com.raulp.report;

import com.raulp.book.model.dto.BookDTO;
import com.raulp.UrlMapping;
import com.raulp.flight.dtos.FlightDTO;
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
    public String export(List<FlightDTO> flights) {

        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[]
                {"Flight ID", "Airplane Description", "Departure Airport", "Departure Airport ICAO"
                        , "Arrival Airport", "Arrival Airport ICAO", "Departure Time",
                        "Arrival Time"
                        , "Student Username", "Student Name", "Student Surname",
                        "Instructor Username", "Instructor Name", "Instructor Surname"
                });
        System.out.println(flights);

        for (int i = 0; i < flights.size(); i++) {
            dataLines.add(new String[] {
                    nullConvert(flights.get(i).getId().toString()),
                    nullConvert(flights.get(i).getAirplane().getName()),
                    nullConvert(flights.get(i).getDepartureAirport().getName()),
                    nullConvert(flights.get(i).getDepartureAirport().getIcao()),
                    nullConvert(flights.get(i).getArrivalAirport().getName()),
                    nullConvert(flights.get(i).getArrivalAirport().getIcao()),
                    nullConvert(flights.get(i).getDepartureTime().toString()),
                    nullConvert(flights.get(i).getArrivalTime().toString()),
                    nullConvert(flights.get(i).getStudent().getName()),
                    nullConvert(flights.get(i).getStudent().getFirstName()),
                    nullConvert(flights.get(i).getStudent().getLastName()),
                    nullConvert(flights.get(i).getInstructor().getName()),
                    nullConvert(flights.get(i).getInstructor().getFirstName()),
                    nullConvert(flights.get(i).getInstructor().getLastName()),
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

    public String nullConvert(String data) {
        if (data == null) {
            return "";
        }
        return data;
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
