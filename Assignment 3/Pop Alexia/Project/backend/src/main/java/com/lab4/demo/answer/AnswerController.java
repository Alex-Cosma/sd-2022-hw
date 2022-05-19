package com.lab4.demo.answer;

import com.lab4.demo.answer.model.dto.AnswerDTO;
import com.lab4.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

import static com.lab4.demo.UrlMapping.ANSWER;

@RestController
@RequestMapping(ANSWER)
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AnswerDTO answerDTO) {
        try {
            return ResponseEntity.ok().body(answerService.create(answerDTO));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getConstraintViolations().iterator().next().getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id , @RequestBody AnswerDTO answerDTO) {
        try {
            answerService.edit(id, answerDTO);
            return ResponseEntity.ok(new MessageResponse("Answer edited successfully"));
        }catch(ConstraintViolationException e) {
            System.out.println("HERE");
            return ResponseEntity.badRequest().body(new MessageResponse(e.getConstraintViolations().iterator().next().getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(answerService.findById(id) == null)
            return ResponseEntity.badRequest().body(new MessageResponse("Answer not found"));
        answerService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Answer deleted successfully"));
    }
}
