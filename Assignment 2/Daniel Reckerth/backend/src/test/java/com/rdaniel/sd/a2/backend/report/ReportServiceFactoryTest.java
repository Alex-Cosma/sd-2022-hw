package com.rdaniel.sd.a2.backend.report;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportServiceFactoryTest {

  @Autowired
  private ReportServiceFactory reportServiceFactory;

  @Test
  void getReportService() {
    ReportService csvReportService = reportServiceFactory.getReportService(ReportType.CSV);
    assertEquals(ReportType.CSV, csvReportService.getReportType());

    ReportService pdfReportService = reportServiceFactory.getReportService(ReportType.PDF);
    assertEquals(ReportType.PDF, pdfReportService.getReportType());
  }
}