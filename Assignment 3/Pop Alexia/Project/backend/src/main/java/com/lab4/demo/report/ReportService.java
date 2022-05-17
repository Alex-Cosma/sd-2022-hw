package com.lab4.demo.report;

import com.lab4.demo.quizz.model.dto.QuizzDTO;
import com.lab4.demo.quizzSession.model.dto.QuizzSessionDTO;

import java.util.List;

public interface ReportService {
    String export(List<QuizzSessionDTO> quizzSessions, List<QuizzDTO> quizzes);

    ReportType getType();

}
