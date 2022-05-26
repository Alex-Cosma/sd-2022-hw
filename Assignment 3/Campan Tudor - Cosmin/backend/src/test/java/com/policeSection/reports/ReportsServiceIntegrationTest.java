package com.policeSection.reports;

import com.policeSection.Reports.Model.Report;
import com.policeSection.Reports.ReportService;
import com.policeSection.Reports.ReportsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReportsServiceIntegrationTest {
    @Autowired
    private ReportService reportService;
    @Autowired
    private ReportsRepository reportsRepository;

    @BeforeEach
    void setUp() {
        reportsRepository.deleteAll();
    }
    @Test
    void create()
    {
        Report report=Report.builder().id("aaa").description("aaa").sectionNumber(6).build();
        Report createdReport=reportService.create(report);
        Assertions.assertEquals(report,createdReport);

    }
    @Test
    void findAll()
    {
        Report report1=Report.builder().id("aaa").description("aaa").sectionNumber(6).build();
        Report report2=Report.builder().id("aaB").description("aaa").sectionNumber(6).build();
        List<Report> list =new ArrayList<>();
        list.add(report1);
        list.add(report2);
        reportsRepository.save(report1);
        reportsRepository.save(report2);
        List<Report> allReports= reportService.allReports();
        assertEquals(list.size(),allReports.size());
    }
}
