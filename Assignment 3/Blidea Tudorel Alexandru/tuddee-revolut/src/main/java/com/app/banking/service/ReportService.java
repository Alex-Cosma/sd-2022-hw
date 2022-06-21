package com.app.banking.service;

import com.app.banking.helper.report.ReportType;
import org.springframework.http.HttpHeaders;


public interface ReportService {

    byte[] export(String content);

    ReportType getType();

    HttpHeaders getHeaders();

}
