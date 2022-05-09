package com.lab4.demo.report;

import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;

import java.io.IOException;
import java.util.List;

public interface ReportService {
    void export(List<Item> items) throws IOException;

    ReportType getType();
}
