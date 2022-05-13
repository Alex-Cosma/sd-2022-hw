package com.example.backend.service.booksOutOfStockReport.jasperReport;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

import java.io.InputStream;

public class JasperPdfReportService {

    public void generate() throws JRException {
        InputStream employeeReportStream
                = getClass().getResourceAsStream("/booksOutOfStockReport.jrxml");
        JasperReport jasperReport
                = JasperCompileManager.compileReport(employeeReportStream);
    }
}
