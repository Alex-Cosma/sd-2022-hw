package com.example.bookstore.report;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



import static com.example.bookstore.UrlMapping.*;
import static com.example.bookstore.report.ReportType.CSV;
import static com.example.bookstore.report.ReportType.PDF;



@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceFactory reportServiceFactory;


    @CrossOrigin
    @PostMapping(EXPORT_REPORT)
    public void generateReport(@PathVariable String type){
        if(type.equals("PDF")){
            reportServiceFactory.getReportService(PDF).export();
        }else{
            reportServiceFactory.getReportService(CSV).export();
        }

    }


}