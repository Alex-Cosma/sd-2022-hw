package com.app.banking.helper.report.pdf;

import com.app.banking.helper.report.pdf.enums.PdfBuilderType;

import java.io.IOException;

public class PdfBuilderFactory {

    private PdfBuilderFactory() {

    }

    public static PdfBuilder getPdfBuilder(PdfBuilderType pdfBuilderType) throws IOException {
        return new ITextBuilder();
    }

}
