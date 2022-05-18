package com.lab4.demo.answer;

import com.lab4.demo.answer.model.dto.AnswerDTO;
import com.lab4.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

import static com.lab4.demo.UrlMapping.ANSWER;

@RestController
@RequestMapping(ANSWER)
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<?> create( @RequestBody AnswerDTO answerDTO) {
        return ResponseEntity.ok().body(answerService.create(answerDTO));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id , @RequestBody AnswerDTO answerDTO) {
        answerService.edit(id, answerDTO);
        return ResponseEntity.ok(new MessageResponse("Answer edited successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        answerService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Answer deleted successfully"));
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
