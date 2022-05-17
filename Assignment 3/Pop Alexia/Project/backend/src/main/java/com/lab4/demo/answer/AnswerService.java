package com.lab4.demo.answer;

import com.lab4.demo.answer.model.Answer;
import com.lab4.demo.answer.model.dto.AnswerDTO;
import com.lab4.demo.question.QuestionMapper;
import com.lab4.demo.question.QuestionService;
import com.lab4.demo.question.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    public AnswerDTO findById(Long id) {
        return answerMapper.toDto(answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found: " + id)));
    }

    public List<AnswerDTO> findAll() {
        return answerRepository.findAll().stream()
                .map(answerMapper::toDto)
                .collect(Collectors.toList());
    }

    public AnswerDTO findByAnswerName(String answerName) {
        return answerMapper.toDto(answerRepository.findByAnswer(answerName));
    }

    public AnswerDTO create(AnswerDTO answerDTO) {
        Answer answer = answerMapper.fromDto(answerDTO);
        Question question = questionMapper.fromDto(questionService.findById(answerDTO.getQuestionId()));
        answer.setQuestion(question);
        return answerMapper.toDto(answerRepository.save(answer));
    }

    public AnswerDTO edit(Long id, AnswerDTO answer) {
        Answer actAnswer = answerRepository.findById(id).get();
        actAnswer.setAnswer(answer.getAnswer());
        actAnswer.setCorrect(answer.getCorrect());

        return answerMapper.toDto(answerRepository.save(actAnswer));
    }

    public void delete(Long id) {
        answerRepository.deleteById(id);
    }

}
