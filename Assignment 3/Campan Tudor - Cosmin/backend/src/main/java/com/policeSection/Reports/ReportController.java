package com.policeSection.Reports;

import com.policeSection.driver.model.dto.DriverDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;

import static com.policeSection.UrlMapping.DRIVERS;
import static com.policeSection.UrlMapping.DWN_PDF;

@RestController
@RequestMapping(DWN_PDF)


@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    @GetMapping
    public ResponseEntity<InputStreamResource> saveReport() {
        ByteArrayInputStream resource= reportService.exportP();
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(resource));
    }
    }

