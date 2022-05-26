package com.policeSection.reports;

import com.policeSection.Reports.Model.Report;
import com.policeSection.Reports.ReportService;
import com.policeSection.Reports.ReportsRepository;
import com.policeSection.fine.Model.Fine;
import com.policeSection.fine.Model.dto.FineDTO;
import com.policeSection.section.Model.Section;
import com.policeSection.section.SectionRepository;
import com.policeSection.section.SectionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReportsServiceTest {
    @InjectMocks
    private ReportService reportService;
    @Mock
    private ReportsRepository reportsRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reportService = new ReportService(reportsRepository);
    }
    @Test
    void create()
    {
        Report report=Report.builder().id("aaa").description("aaa").sectionNumber(6).build();
        when(reportsRepository.save(report)).thenReturn(report);
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
        when(reportsRepository.findAll()).thenReturn(list);
        List<Report> allReports= reportService.allReports();
        assertEquals(list.size(),allReports.size());
    }
}
