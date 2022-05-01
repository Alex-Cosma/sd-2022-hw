package com.assignment2.bookstoreapp.report;

import com.assignment2.bookstoreapp.report.dto.ReportDTO;

import java.util.ArrayList;

public interface ReportService {

    <T> boolean export(ReportDTO<T> reportDTO);

    ReportType getType();

}
