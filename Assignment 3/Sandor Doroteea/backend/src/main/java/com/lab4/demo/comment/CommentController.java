package com.lab4.demo.comment;

import com.lab4.demo.comment.model.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static com.lab4.demo.UrlMapping.COMMENTS;
import static com.lab4.demo.UrlMapping.COMMENT_ID_PART;

@RestController
@RequestMapping(COMMENTS)
@RequiredArgsConstructor

public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDTO> allComments()  {
        return commentService.findAll();
    }

    @PostMapping
    public CommentDTO create(@RequestBody CommentDTO commentDTO) {
        return commentService.save(commentDTO);
    }

    @PutMapping(COMMENT_ID_PART)
    public CommentDTO update(@PathVariable String id,@RequestBody CommentDTO commentDTO) {
        return commentService.update(id, commentDTO);
    }

    @DeleteMapping(COMMENT_ID_PART)
    public void delete(@PathVariable String id) {
        commentService.delete(id);
    }
}
