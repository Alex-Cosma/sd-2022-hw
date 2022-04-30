package com.assignment2.bookstoreapp.report;


import org.springframework.stereotype.Service;

import static com.assignment2.bookstoreapp.report.ReportType.PDF;

@Service
public class PDFReportService implements ReportService {
    @Override
    public String export() {
        return "I am a PDF reporter.";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
