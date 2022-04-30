package com.assignment2.bookstoreapp.report;

import org.springframework.stereotype.Service;

import static com.assignment2.bookstoreapp.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {
    @Override
    public String export() {
        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
