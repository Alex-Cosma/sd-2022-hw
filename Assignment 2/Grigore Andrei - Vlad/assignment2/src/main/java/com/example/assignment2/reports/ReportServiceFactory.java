package com.example.assignment2.reports;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportServiceFactory {
    private final Map<ReportType,ReportService> reportServices;

    public ReportServiceFactory(List<ReportService> reportServices){
        this.reportServices  = reportServices.stream()
                .collect(Collectors.toMap(ReportService::getType, Function.identity()));
        for(ReportService r:reportServices){
            System.out.println(r.getType());
        }
    }

    public ReportService getReportService(ReportType type){
        return reportServices.get(type);
    }
}
