package com.forum.thread;

import com.forum.user.UserRepository;
import com.forum.category.CategoryRepository;
import com.forum.post.PostRepository;
import com.forum.post.model.Post;
import com.forum.thread.mapper.TopicMapper;
import com.forum.thread.model.Topic;
import com.forum.thread.model.dto.TopicDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final PostRepository postRepository;

    public Topic findById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found: " + id));
    }

    public List<TopicDTO> findAll() {
        return topicRepository.findAll().stream()
                .map(topic ->
                        {
                            TopicDTO dto = topicMapper.toDto(topic);
                            topicMapper.setCategoryAndOP(topic, dto);
                            return dto;
                        }
                )
                .collect(Collectors.toList());
    }

    public List<TopicDTO> findAllByCategoryId(Long categoryId) {
        return topicRepository.findAllByCategoryId(categoryId).stream()
                .map(topic ->
                        {
                            TopicDTO dto = topicMapper.toDto(topic);
                            topicMapper.setCategoryAndOP(topic, dto);
                            return dto;
                        }
                )
                .collect(Collectors.toList());
    }

    public TopicDTO findTopicById(Long id) {
        return topicMapper.toDto(findById(id));
    }

    public TopicDTO create(TopicDTO topicDTO) {
        Topic topic = topicMapper.fromDto(topicDTO);
        topic.setCategory(categoryRepository.findByName(topicDTO.getCategoryName())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + topicDTO.getCategoryName())));
        topic.setUser(
                userRepository.findByUsername(topicDTO.getOriginalPoster())
                        .orElseThrow(() -> new EntityNotFoundException("User not found: " + topicDTO.getOriginalPoster()))
        );
        return topicMapper.toDto(topicRepository.save(topic));
    }

    public TopicDTO edit(Long id, TopicDTO topicDTO) {
        Topic topic = findById(id);
        topic.setCategory(categoryRepository.findByName(topicDTO.getCategoryName())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + topicDTO.getCategoryName())));
        topic.setUser(
                userRepository.findByUsername(topicDTO.getOriginalPoster())
                        .orElseThrow(() -> new EntityNotFoundException("User not found: " + topicDTO.getOriginalPoster()))
        );
        topic.setTitle(topicDTO.getTitle());
        topic.setContent(topicDTO.getContent());
        topic.setCreationDate(topicDTO.getCreationDate());
        return topicMapper.toDto(topicRepository.save(topic));
    }

    public TopicDTO delete(Long id) {
        List<Post> posts = postRepository.findAllByTopicId(id);
        postRepository.deleteAll(posts);

        Topic topic = findById(id);
        topicRepository.delete(topic);
        return topicMapper.toDto(topic);
    }
}
