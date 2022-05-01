package com.example.backend.service.booksOutOfStockReport.jasperReport;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRSaver;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleReportFiller {

    private String reportFileName;
    private JasperReport jasperReport;
    private JasperPrint jasperPrint;
    private Map<String, Object> parameters;

    public SimpleReportFiller() {
        parameters = new HashMap<>();
    }

    public void prepareReport() {
        compileReport();
    }

    public void compileReport() {
        try {
            ClassLoader classLoader = SimpleReportFiller.class.getClassLoader();
            InputStream reportStream = classLoader.getResourceAsStream("booksOutOfStockReport.jrxml");

            jasperReport = JasperCompileManager.compileReport(reportStream);
            JRSaver.saveObject(jasperReport, reportFileName.replace(".jrxml", ".jasper"));
        } catch (JRException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }
}
