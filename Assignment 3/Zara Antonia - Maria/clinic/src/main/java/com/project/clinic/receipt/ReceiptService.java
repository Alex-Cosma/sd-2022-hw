package com.project.clinic.receipt;

import com.project.clinic.receipt.dto.ReceiptDTO;
import java.io.File;

public interface ReceiptService {

    File export(ReceiptDTO receiptDTO);

    ReceiptType getType();

}
