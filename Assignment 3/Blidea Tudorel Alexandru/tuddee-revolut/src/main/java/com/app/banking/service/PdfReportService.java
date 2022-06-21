package com.app.banking.service;


import com.app.banking.helper.ServiceHelper;
import com.app.banking.helper.report.ReportType;
import com.app.banking.helper.report.pdf.PdfBuilderFactory;
import com.app.banking.helper.report.pdf.enums.PdfBuilderType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

import static com.app.banking.helper.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {

    @Override
    public byte[] export(String content) {
        try {
            return PdfBuilderFactory.getPdfBuilder(PdfBuilderType.ITEXT)
                    .addTitle("Report on " + ServiceHelper.dateToString(LocalDate.now()))
                    .setCurrentFontSize(10.0f)
                    .addParagraph(content)
                    .buildToByteArray();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public ReportType getType() {
        return PDF;
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("output.pdf", "output.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
    }

}
