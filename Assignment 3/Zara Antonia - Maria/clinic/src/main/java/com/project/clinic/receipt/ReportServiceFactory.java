package com.project.clinic.receipt;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportServiceFactory {
    private final Map<ReceiptType, ReceiptService> reportServices;

    public ReportServiceFactory(List<ReceiptService> reportServices) {
        this.reportServices = reportServices.stream()
                .collect(Collectors.toMap(ReceiptService::getType, Function.identity()));
    }

    public ReceiptService getReportService(ReceiptType type) {
        return reportServices.get(type);
    }
}