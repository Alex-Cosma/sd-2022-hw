package com.lab4.demo.question;

import com.lab4.demo.question.model.Question;
import com.lab4.demo.question.model.dto.QuestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public QuestionDTO findById(Long id) {
        return questionMapper.toDto(questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found: " + id)));
    }

    public List<QuestionDTO> findAll() {
        return questionRepository.findAll().stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    public QuestionDTO findByQuestionName(String questionName) {
        return questionMapper.toDto(questionRepository.findByStatement(questionName));
    }

    public List<QuestionDTO> filterQuestions(String filter){
        PageRequest pageRequest = PageRequest.of(0, 10);
        return questionRepository.findAllByCategoryLikeOrStatementLike("%"+filter+"%","%"+filter+"%",pageRequest).stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    public QuestionDTO create(QuestionDTO question) throws ConstraintViolationException {
        return questionMapper.toDto(questionRepository.save(questionMapper.fromDto(question)));
    }

    public QuestionDTO edit(Long id, QuestionDTO question) throws ConstraintViolationException {
        Question actQuestion = questionRepository.findById(id).get();
        actQuestion.setStatement(question.getStatement());
        actQuestion.setCategory(question.getCategory());

        return questionMapper.toDto(questionRepository.save(actQuestion));
    }

    public void delete(Long id) throws DataIntegrityViolationException {
        questionRepository.deleteById(id);
    }


}
