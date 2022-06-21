package com.app.banking.helper.report.pdf;

import com.app.banking.helper.report.pdf.enums.PdfTextPosition;

import java.io.Closeable;
import java.io.IOException;

public interface PdfBuilder extends Closeable {

    PdfBuilder addParagraph(String paragraph) throws IOException;

    PdfBuilder addTitle(String title) throws IOException;

    PdfBuilder setCurrentFontSize(Float fontSize) throws IOException;

    PdfBuilder setCurrentTextPosition(PdfTextPosition textPosition);

    byte[] buildToByteArray() throws IOException;

}