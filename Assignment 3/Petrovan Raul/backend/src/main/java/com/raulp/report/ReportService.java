package com.raulp.report;

import com.raulp.flight.dtos.FlightDTO;

import java.io.IOException;
import java.util.List;

public interface ReportService {
    String export(List<FlightDTO> flights) throws IOException;

    ReportType getType();
}
