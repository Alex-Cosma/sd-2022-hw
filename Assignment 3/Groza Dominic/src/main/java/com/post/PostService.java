package com.post;

import com.post.model.Post;
import com.post.model.dto.PostDto;
import com.user.UserService;
import com.user.dto.UserListDto;
import com.user.mapper.UserMapper;
import com.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final UserService userService;

    private Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found with id :"+id));
    }

    public List<PostDto> findAll(){
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public PostDto create (PostDto postDto){
        postDto.setCreated_at(Date.from(new Date().toInstant()));
        postDto.setLikes(0L);
        postDto.setDisLikes(0L);
        Post post = postMapper.fromDto(postDto);
        return postMapper.toDto(postRepository.save(post));
    }

    public PostDto edit(Long id, PostDto postDto){
        Post post = findById(id);
        post.setBody(postDto.getBody());
        post.setLikes(postDto.getLikes());
        post.setDisLikes(postDto.getDisLikes());
        post.setUser(postDto.getUser());
        return postMapper.toDto(postRepository.save(post));
    }

    public void delete(Long id){
        postRepository.deleteById(id);
    }

    public PostDto get(Long id){
        return postMapper.toDto(findById(id));
    }

    public List<PostDto> findByUserId(Long userId){
        return postRepository.findByUserId(userId).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> findPostsOfFriends(Long userId){
        UserListDto user=userService.get(userId);
        List<PostDto> posts=new ArrayList<>();
        System.out.println(user.toString());
        for (User friend : userService.getFriends(userId)) {
            List<PostDto> foundPosts = findByUserId(friend.getId());
            posts.addAll(foundPosts);
        }
        return posts;
    }


}
