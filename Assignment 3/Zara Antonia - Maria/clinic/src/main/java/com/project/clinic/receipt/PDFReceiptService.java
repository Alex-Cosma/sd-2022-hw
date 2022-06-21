package com.project.clinic.receipt;


import com.project.clinic.receipt.dto.ReceiptDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static com.project.clinic.receipt.ReceiptType.PDF;

@Service
public class PDFReceiptService implements ReceiptService {

    @Override
    public File export(ReceiptDTO receiptDTO) {
        try {

            File file = new File(getReportTitle());

            int xOffset = 50;
            int yOffSet = 720;

            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);

            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(xOffset, yOffSet);
            yOffSet -= 20;
            content.showText("Your receipt for: ");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(xOffset, yOffSet);
            yOffSet -= 20;
            content.showText("PRODUCT: " + receiptDTO.getProductName().replace(" ","-"));
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(xOffset, yOffSet);
            yOffSet -= 20;
            content.showText("PRICE: " + receiptDTO.getPrice());
            content.endText();

            content.close();

            doc.save(file);
            doc.close();

            return file;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ReceiptType getType() {
        return PDF;
    }

    private String getReportTitle(){
        return ("Report" + LocalDateTime.now() + ".pdf").replace(':','-');
    }
}
