package com.example.youtubeish.comment;

import com.example.youtubeish.comment.model.dto.AddCommentDTO;
import com.example.youtubeish.comment.model.dto.CommentDTO;
import com.example.youtubeish.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.youtubeish.UrlMapping.*;

@RestController
@RequestMapping(COMMENT)
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @GetMapping(GET_COMMENTS)
    public List<CommentDTO> getCommentFromVideo(@PathVariable Long id) {
        return commentService.getAllCommentsFromVideo(id);
    }

    @PostMapping(ADD_COMMENT)
    public ResponseEntity<?> addComment(@RequestBody AddCommentDTO addCommentDTO) {
        commentService.create(addCommentDTO.getContent(), addCommentDTO.getUser(), addCommentDTO.getVideo());
        return ResponseEntity.ok(new MessageResponse("Comment added successfully!"));
    }
}
