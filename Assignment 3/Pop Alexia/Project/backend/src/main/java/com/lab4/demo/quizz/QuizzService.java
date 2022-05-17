package com.lab4.demo.quizz;

import com.lab4.demo.quizz.model.Quizz;
import com.lab4.demo.quizz.model.dto.QuizzDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizzService {

    private final QuizzRepository quizzRepository;
    private final QuizzMapper quizzMapper;

    public Quizz findById(Long id) {
        return quizzRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found: " + id));
    }

    public List<QuizzDTO> findAll() {
        return quizzRepository.findAll().stream()
                .map(quizzMapper::toDto)
                .collect(Collectors.toList());
    }

    public QuizzDTO findByQuizzTitle(String title) {
        return quizzMapper.toDto(quizzRepository.findByTitle(title));
    }

    public List<QuizzDTO> filterQuizzes(String filter){
        PageRequest pageRequest = PageRequest.of(0, 10);
        return quizzRepository.findAllByTitleLikeOrDescriptionLike("%"+filter+"%","%"+filter+"%",pageRequest)
                .stream()
                .map(quizzMapper::toDto)
                .collect(Collectors.toList());
    }

    public QuizzDTO create(QuizzDTO quizz) {
        return quizzMapper.toDto(quizzRepository.save(quizzMapper.fromDto(quizz)));
    }

    public QuizzDTO edit(Long id, QuizzDTO quizz) {
        Quizz actQuizz = quizzRepository.findById(id).get();
        actQuizz.setDescription(quizz.getDescription());
        actQuizz.setTitle(quizz.getTitle());
        actQuizz.setQuestions(quizz.getQuestions());
        actQuizz.setPoints(quizz.getPoints());

        return quizzMapper.toDto(quizzRepository.save(actQuizz));
    }

    public void delete(Long id) {
        quizzRepository.deleteById(id);
    }

}
