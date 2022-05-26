package com.forum.post;

import com.forum.UrlMapping;
import com.forum.post.model.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.forum.UrlMapping.*;

@RestController
@RequestMapping(POST)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public List<PostDTO> allPosts() {
        return postService.findAll();
    }

    @GetMapping(OF_THREAD_ID_PART)
    public List<PostDTO> allPostsOfThread(@PathVariable("id") Long threadId) {
        return postService.findAllByTopicId(threadId);
    }

    @PostMapping
    public PostDTO create(@RequestBody PostDTO post) {
        return postService.create(post);
    }

    @PutMapping(POST_ID_PART)
    public PostDTO edit(@PathVariable Long id, @RequestBody PostDTO post) {
        return postService.edit(id, post);
    }

    @DeleteMapping(POST_ID_PART)
    public PostDTO delete(@PathVariable Long id) {
        return postService.delete(id);
    }
}
