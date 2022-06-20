package com.example.gymapplication.report;


import com.example.gymapplication.training.model.dto.TrainingDTO;

import java.util.List;

public interface ReportService {
    String export(List<TrainingDTO> trainings, String username);

    ReportType getType();
}
