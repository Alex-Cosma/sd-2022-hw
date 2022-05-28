package com.lab4.demo.question;

import com.lab4.demo.question.model.dto.QuestionDTO;
import com.lab4.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.lab4.demo.UrlMapping.QUESTION;

@RestController
@RequestMapping(QUESTION)
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


    @GetMapping
    public List<QuestionDTO> allQuestions() {
        return questionService.findAll();
    }

    @GetMapping("/filter/{filter}")
    public List<QuestionDTO> filterQuestions(@PathVariable String filter){return questionService.filterQuestions(filter);}

    @PostMapping
    public ResponseEntity<?> create( @RequestBody QuestionDTO questionDTO) {
        if(questionService.findByQuestionName(questionDTO.getStatement())!=null)
          return ResponseEntity.badRequest().body(new MessageResponse("This question already exists"));
        try {
            return ResponseEntity.ok().body(questionService.create(questionDTO));
        }catch(ConstraintViolationException e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getConstraintViolations().iterator().next().getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id ,  @RequestBody QuestionDTO questionDTO) {
        if(!questionService.findById(id).getStatement().equals(questionDTO.getStatement())) {
           if(questionService.findByQuestionName(questionDTO.getStatement())!=null){
               return ResponseEntity.badRequest().body(new MessageResponse("This question already exists"));
           }
        }
        try {
            questionService.edit(id, questionDTO);
            return ResponseEntity.ok(new MessageResponse("Question edited successfully"));
        }catch(ConstraintViolationException e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getConstraintViolations().iterator().next().getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(questionService.findById(id) == null)
            return ResponseEntity.badRequest().body(new MessageResponse("Question not found"));
        try {
            questionService.delete(id);
            return ResponseEntity.ok(new MessageResponse("Question deleted successfully"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Question can't be deleted , it is part of a quizz"));
        }

    }
}
