package com.lab4.demo.report;

import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class ReportController {
    private final ItemService itemService;
    private final PdfReportService pdfReportService;
    private final CSVReportService csvReportService;

    @GetMapping(EXPORT_PDF)
    public String reportPDF() {
        return pdfReportService.exportP(itemService.findItems0());
    }
    @GetMapping(EXPORT_CSV)
    public String reportCSV() {
        return csvReportService.exportC(itemService.findItems0());
    }
}
