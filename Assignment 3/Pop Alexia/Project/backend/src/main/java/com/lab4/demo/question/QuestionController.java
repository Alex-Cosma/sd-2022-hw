package com.lab4.demo.question;

import com.lab4.demo.question.model.dto.QuestionDTO;
import com.lab4.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static com.lab4.demo.UrlMapping.*;

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
        if(questionService.findByQuestionName(questionDTO.getStatement())!=null){
          throw new ConstraintViolationException("This question already exists", Set.of());
        }
        return ResponseEntity.ok().body(questionService.create(questionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id ,  @RequestBody QuestionDTO questionDTO) {
        if(!questionService.findById(id).getStatement().equals(questionDTO.getStatement())) {
           if(questionService.findByQuestionName(questionDTO.getStatement())!=null){
               throw new ConstraintViolationException("This question already exists", Set.of());
           }
        }
        questionService.edit(id, questionDTO);
        return ResponseEntity.ok(new MessageResponse("Question edited successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Question deleted successfully"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse(e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse(e.getMessage()));
    }
}
