package com.raulp.report;

import com.raulp.book.BookService;
import com.raulp.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping(UrlMapping.REPORT)
@RequiredArgsConstructor
public class ReportController {
    @Autowired
    private ReportServiceFactory reportServiceFactory;
    @Autowired
    private BookService bookService;

    @GetMapping(UrlMapping.PDF)
    public ResponseEntity<Resource> getReportPDF() throws IOException {
        System.out.println("PDF");
        bookService.export(ReportType.PDF);

        File file = new File(UrlMapping.REPORT_LOCAL_PATH + UrlMapping.REPORT_LOCAL_PDF);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping(UrlMapping.CSV)
    public ResponseEntity<Resource> getReportCSV() throws IOException {
        bookService.export(ReportType.CSV);

        File file = new File(UrlMapping.REPORT_LOCAL_PATH + UrlMapping.REPORT_LOCAL_CSV);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
