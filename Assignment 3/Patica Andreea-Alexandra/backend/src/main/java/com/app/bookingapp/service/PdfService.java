package com.app.bookingapp.service;

import com.app.bookingapp.data.dto.model.BookDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class PdfService {
    private static final Font boldTitle = new Font(Font.FontFamily.TIMES_ROMAN, 28, Font.BOLDITALIC);
    private static final Font boldSmall = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLDITALIC);

    public static byte[] createPdf(BookDto book)throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();

        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        document.add(new Header("My Booking",""));

        document.add(new Paragraph("Booking",boldTitle));
        document.add(new Paragraph("Property name: " + book.getProperty().getName()));
        document.add(new Paragraph("Property address: " + book.getProperty().getAddress(),boldSmall));
        document.add(new Paragraph("Booked date: " + book.getDate().toString(),boldSmall));
        document.add(new Paragraph("Total Price: " + book.getProperty().getPrice(),boldSmall));
        document.add(new Paragraph("Owner phone number: " + book.getProperty().getOwner().getPhoneNumber(),boldSmall));
        document.add(new Paragraph("Owner email address: " + book.getProperty().getOwner().getEmail(),boldSmall));

        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
