package com.app.banking.controller;

import com.app.banking.helper.report.ReportServiceFactory;
import com.app.banking.helper.report.ReportType;
import com.app.banking.service.ReportDataService;
import com.app.banking.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

import static com.app.banking.URLMapping.REPORTS;

@RestController
@RequestMapping(REPORTS)
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:*")
public class ReportController {

    private final ReportServiceFactory reportServiceFactory;
    private final ReportDataService reportDataService;

    @GetMapping("/my_cards/{type}")
    public ResponseEntity<byte[]> getPersonalCardsReport(Principal principal, @PathVariable ReportType type) {
        return new ResponseEntity<>(
                reportServiceFactory.getReportService(type).export(reportDataService.getOwnCardsReport(principal.getName())),
                reportServiceFactory.getReportService(type).getHeaders(),
                HttpStatus.OK);
    }

}
