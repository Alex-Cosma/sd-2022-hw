package com.lab4.demo.quizzSession;

import com.lab4.demo.quizz.QuizzMapper;
import com.lab4.demo.quizz.QuizzService;
import com.lab4.demo.quizz.model.Quizz;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import com.lab4.demo.quizzSession.model.QuizzSession;
import com.lab4.demo.quizzSession.model.dto.QuizzSessionDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizzSessionService {
    private final QuizzSessionRepository quizzSessionRepository;
    private final QuizzSessionMapper quizzSessionMapper;
    private final ReportServiceFactory reportServiceFactory;
    private final UserService userService;
    private final QuizzService quizzService;
    private final QuizzMapper quizzMapper;


    public QuizzSessionDTO findById(Long id) {
        return quizzSessionMapper.toDto(quizzSessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found: " + id)));
    }

    public List<QuizzSessionDTO> findAll() {
        return quizzSessionRepository.findAll().stream()
                .map(quizzSessionMapper::toDto)
                .collect(Collectors.toList());
    }

    public QuizzSessionDTO create(QuizzSessionDTO quizzSession) {
        QuizzSession entity = quizzSessionMapper.fromDto(quizzSession);
        User user = userService.findById(quizzSession.getUserId());
        Quizz quizz = quizzService.findById(quizzSession.getQuizzId());
        user.setRankingPoints(user.getRankingPoints() + quizzSession.getScore());
        entity.setUser(user);
        entity.setQuizz(quizz);
        return quizzSessionMapper.toDto(quizzSessionRepository.save(entity));
    }

    public String export(ReportType type,Long id) {
        List<QuizzSessionDTO> quizzSessions = findAll().stream().filter(q->q.getUserId().equals(id)).collect(Collectors.toList());
        List<QuizzDTO> quizzes = new ArrayList<>();
        for(QuizzSessionDTO q:quizzSessions){
            quizzes.add(quizzMapper.toDto(quizzService.findById(q.getQuizzId())));
        }
        return reportServiceFactory.getReportService(type).export(quizzSessions,quizzes);
    }


}
