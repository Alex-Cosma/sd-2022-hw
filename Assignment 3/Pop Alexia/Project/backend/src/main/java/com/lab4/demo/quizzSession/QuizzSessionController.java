package com.lab4.demo.quizzSession;

import com.lab4.demo.quizzSession.model.dto.QuizzSessionDTO;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
import static com.lab4.demo.UrlMapping.QUIZZ_SESSION;

@RestController
@RequestMapping(QUIZZ_SESSION)
@RequiredArgsConstructor
public class QuizzSessionController {
    private final QuizzSessionService quizzSessionService;

    @GetMapping
    public List<QuizzSessionDTO> allQuizzSessions() {
        return quizzSessionService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody QuizzSessionDTO quizzDTO) {
        return ResponseEntity.ok().body(quizzSessionService.create(quizzDTO));
    }

    @GetMapping(EXPORT_REPORT+"/{id}")
    public ResponseEntity<Resource> exportReport(@PathVariable ReportType type, @PathVariable Long id) {
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(quizzSessionService.export(type, id)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(resource);
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
