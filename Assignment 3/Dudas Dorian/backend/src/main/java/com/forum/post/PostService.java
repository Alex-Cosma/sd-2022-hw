package com.forum.post;

import com.forum.post.mapper.PostMapper;
import com.forum.post.model.dto.PostDTO;
import com.forum.user.UserRepository;
import com.forum.post.model.Post;
import com.forum.thread.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + id));
    }

    public List<PostDTO> findAll() {
        return postRepository.findAll().stream()
                .map(post -> {
                    PostDTO dto = postMapper.toDto(post);
                    postMapper.setTopicAndUser(post, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<PostDTO> findAllByTopicId(Long topicId) {
        return postRepository.findAllByTopicId(topicId).stream()
                .map(post -> {
                    PostDTO dto = postMapper.toDto(post);
                    postMapper.setTopicAndUser(post, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public PostDTO create(PostDTO postDTO) {
        Post post = postMapper.fromDto(postDTO);
        post.setTopic(
                topicRepository.findByTitle(postDTO.getTopicTitle())
                        .orElseThrow(() -> new EntityNotFoundException("Topic not found: " + postDTO.getTopicTitle()))
        );
        post.setUser(
                userRepository.findByUsername(postDTO.getPosterUsername())
                        .orElseThrow(() -> new EntityNotFoundException("User not found: " + postDTO.getPosterUsername()))
        );
        return postMapper.toDto(postRepository.save(post));
    }

    public PostDTO edit(Long id, PostDTO postDTO) {
        Post post = findById(id);
        post.setTopic(
                topicRepository.findByTitle(postDTO.getTopicTitle())
                        .orElseThrow(() -> new EntityNotFoundException("Topic not found: " + postDTO.getTopicTitle()))
        );
        post.setUser(
                userRepository.findByUsername(postDTO.getPosterUsername())
                        .orElseThrow(() -> new EntityNotFoundException("User not found: " + postDTO.getPosterUsername()))
        );
        post.setContent(postDTO.getContent());
        post.setCreationDate(postDTO.getCreationDate());
        return postMapper.toDto(postRepository.save(post));
    }

    public PostDTO delete(Long id) {
        Post post = findById(id);
        postRepository.delete(post);
        return postMapper.toDto(post);
    }
}
