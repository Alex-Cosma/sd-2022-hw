package com.lab4.demo.report;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ReportService {
    public ResponseEntity<byte[]> export(Long userId) throws IOException;

    ReportType getType();
}
