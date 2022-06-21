package com.example.demo.report;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

import static com.example.demo.UrlMapping.REPORT;


@RestController
@RequestMapping(REPORT)
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> getReport(){
        ByteArrayInputStream report= reportService.exportReport();

        return ResponseEntity.ok().body(new InputStreamResource(report));
    }
}
