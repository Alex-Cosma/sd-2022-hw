package com.lab4.demo.quizzSession;

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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizzSessionService {
    private final QuizzSessionRepository quizzSessionRepository;
    private final QuizzSessionMapper quizzMapper;
    private final ReportServiceFactory reportServiceFactory;
    private final UserService userService;
    private final QuizzService quizzService;


    public QuizzSessionDTO findById(Long id) {
        return quizzMapper.toDto(quizzSessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found: " + id)));
    }

    public List<QuizzSessionDTO> findAll() {
        return quizzSessionRepository.findAll().stream()
                .map(quizzMapper::toDto)
                .collect(Collectors.toList());
    }

    public QuizzSessionDTO create(QuizzSessionDTO quizzSession) {
        QuizzSession entity = quizzMapper.fromDto(quizzSession);
        User user = userService.findById(quizzSession.getUserId());
        Quizz quizz = quizzService.findById(quizzSession.getQuizzId());
        user.setRankingPoints(user.getRankingPoints() + quizzSession.getScore());
        entity.setUser(user);
        entity.setQuizz(quizz);
        return quizzMapper.toDto(quizzSessionRepository.save(entity));
    }

    public String export(ReportType type,Long id) {
        List<QuizzSessionDTO> quizzSessions = findAll().stream().filter(q->q.getUserId().equals(id)).collect(Collectors.toList());
        List<QuizzDTO> quizzes = quizzService.findAll().stream().filter(q->q.getId().equals(quizzSessions.get(0).getQuizzId())).collect(Collectors.toList());
        return reportServiceFactory.getReportService(type).export(quizzSessions,quizzes);
    }


}
