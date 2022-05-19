package com.lab4.demo.quizz;

import com.lab4.demo.quizz.model.dto.QuizzDTO;
import com.lab4.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.lab4.demo.UrlMapping.QUIZZ;

@RestController
@RequestMapping(QUIZZ)
@RequiredArgsConstructor
public class QuizzController {

    private final QuizzService quizzService;

    @GetMapping
    public List<QuizzDTO> allQuizzes() {
        return quizzService.findAll();
    }

    @GetMapping("/filter/{filter}")
    public List<QuizzDTO> filterQuizzes(@PathVariable String filter){return quizzService.filterQuizzes(filter);}

    @PostMapping
    public ResponseEntity<?> create( @RequestBody QuizzDTO quizzDTO) {
        if(quizzService.findByQuizzTitle(quizzDTO.getTitle())!=null){
            return ResponseEntity.badRequest().body(new MessageResponse("This quizz already exists"));
        }
        try {
            return ResponseEntity.ok().body(quizzService.create(quizzDTO));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getConstraintViolations().iterator().next().getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id ,  @RequestBody QuizzDTO quizzDTO) {
        if(!quizzService.findById(id).getTitle().equals(quizzDTO.getTitle())) {
            if(quizzService.findByQuizzTitle(quizzDTO.getTitle())!=null){
                return ResponseEntity.badRequest().body(new MessageResponse("This quizz already exists"));
            }
        }
        try {
            quizzService.edit(id, quizzDTO);
            return ResponseEntity.ok(new MessageResponse("Quizz edited successfully"));
        }catch (ConstraintViolationException e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getConstraintViolations().iterator().next().getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(quizzService.findById(id)==null)
            return ResponseEntity.badRequest().body(new MessageResponse("Quizz not found"));
        quizzService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Quizz deleted successfully"));
    }
}
