package com.post;

import com.post.model.Post;
import com.post.model.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found with id :"+id));
    }

    public List<PostDto> findAll(){
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public PostDto create (PostDto postDto){

        Post post = postMapper.fromDto(postDto);
        System.out.println("-----------" +post.getCreated_at());
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

    public List<Post> findByUserId(Long userId){
        return postRepository.findByUserId(userId);
    }


}
