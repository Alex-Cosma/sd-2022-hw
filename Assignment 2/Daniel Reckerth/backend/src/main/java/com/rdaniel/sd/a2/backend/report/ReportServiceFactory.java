package com.rdaniel.sd.a2.backend.report;

import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportServiceFactory {
  private final Map<ReportType, ReportService> reportServices;

  public ReportServiceFactory(List<ReportService> reportServices) {
    this.reportServices = reportServices.stream()
        .collect(Collectors.toMap(ReportService::getReportType, Function.identity()));
  }

  public String export(ReportType type) throws JRException, FileNotFoundException {
    return reportServices.get(type).export();
  }

  public ReportService getReportService(ReportType type) {
    return reportServices.get(type);
  }
}
