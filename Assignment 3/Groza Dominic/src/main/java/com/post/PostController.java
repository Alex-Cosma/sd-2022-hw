package com.post;

import com.post.model.dto.PostDto;
import com.user.UserService;
import com.user.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.UrlMapping.ENTITY;
import static com.UrlMapping.POSTS;

@RestController
@RequestMapping(POSTS)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @CrossOrigin
    @GetMapping
    public List<PostDto> allPosts() {
        return postService.findAll();

    }

    @CrossOrigin
    @PostMapping
    public PostDto create(@RequestBody PostDto postDto) {
        return postService.create(postDto);
    }

    @CrossOrigin
    @PutMapping(ENTITY)
    public PostDto edit(@PathVariable Long id, @RequestBody PostDto post) {
        return postService.edit(id, post);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {

        postService.delete(id);
    }

    @CrossOrigin
    @GetMapping(ENTITY)
    public PostDto getPost(@PathVariable Long id) {
        return postService.get(id);
    }
}
