package com.lab4.demo.answer;

import com.lab4.demo.answer.model.dto.AnswerDTO;
import com.lab4.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Set;

import static com.lab4.demo.UrlMapping.ANSWER;

@RestController
@RequestMapping(ANSWER)
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

   // @GetMapping
   // public List<AnswerDTO> allQuestions() {
       // return answerService.findAll();
    //}

    @PostMapping
    public ResponseEntity<?> create( @RequestBody AnswerDTO answerDTO) {
        return ResponseEntity.ok().body(answerService.create(answerDTO));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id , @RequestBody AnswerDTO answerDTO) {
        if (!answerService.findById(id).getAnswer().equals(answerDTO.getAnswer())) {
            if (answerService.findByAnswerName(answerDTO.getAnswer()) != null) {
                throw new ConstraintViolationException("This answer already exists", Set.of());
            }
        }
        answerService.edit(id, answerDTO);
        return ResponseEntity.ok(new MessageResponse("Answer edited successfully"));
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
