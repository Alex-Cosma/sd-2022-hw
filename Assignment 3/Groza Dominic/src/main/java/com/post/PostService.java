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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final UserService userService;

    private Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found with id :" + id));
    }

    public List<PostDto> findAll() {
        return postRepository.findAll().stream()
                .map(post -> {
                    PostDto postDto = postMapper.toDto(post);


                    UserListDto userListDto=userMapper.userListDtoFromUser(post.getUser());
                    userMapper.populateFriends(post.getUser(),userListDto);

                    postDto.setUser(userListDto);
                    return postDto;
                })
                .collect(Collectors.toList());
    }

    public PostDto create(Long userId,PostDto postDto) {
        UserListDto userListDto=userService.get(userId);
        postDto.setCreated_at(Date.from(new Date().toInstant()));
        postDto.setLikes(0L);
        postDto.setDisLikes(0L);
        Post post = postMapper.fromDto(postDto);
        post.setUser(userMapper.userFromUserListDto(userListDto));
        return postMapper.toDto(postRepository.save(post));
    }

    public PostDto edit(Long id, PostDto postDto) {
        Post post = findById(id);
        post.setBody(postDto.getBody());
        post.setLikes(postDto.getLikes());
        post.setDisLikes(postDto.getDisLikes());
        User user=userMapper.userFromUserListDto(postDto.getUser());
        post.setUser(user);
        System.out.println("POST IS THIS++++"+user+"--"+post.getUser());
        System.out.println("postDto   "+postDto);
        System.out.println("salvat   "+postMapper.toDto(postRepository.save(post)));
        return postMapper.toDto(postRepository.save(post));
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public PostDto get(Long id) {
        return postMapper.toDto(findById(id));
    }

    public List<PostDto> findByUserId(Long userId) {
        return postRepository.findByUserId(userId).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }



}
