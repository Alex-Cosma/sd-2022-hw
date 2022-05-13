package com.lab4.demo.report;

import java.io.IOException;

public interface ReportService {
    String export() throws IOException;

    ReportType getType();
}
