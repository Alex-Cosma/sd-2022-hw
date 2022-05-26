package com.forum.category;

import com.forum.category.mapper.CategoryMapper;
import com.forum.category.model.Category;
import com.forum.category.model.dto.CategoryDTO;
import com.forum.post.PostRepository;
import com.forum.thread.TopicRepository;
import com.forum.thread.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;

    public Category findById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<CategoryDTO> findAll(){
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDTO findCategoryById(Long id){
        return categoryMapper.toDto(findById(id));
    }

    public CategoryDTO create(CategoryDTO categoryDTO){
        Category category = categoryMapper.fromDto(categoryDTO);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public CategoryDTO edit(Long id, CategoryDTO categoryDTO){
        Category category = findById(id);
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public CategoryDTO delete(Long id){
        List<Topic> topics = topicRepository.findAllByCategoryId(id);
        List<Long> topicIds = topics.stream().map(Topic::getId).collect(Collectors.toList());
        for(Long topicId : topicIds){
            postRepository.deleteAll(postRepository.findAllByTopicId(topicId));
        }
        topicRepository.deleteAll(topics);

        Category category = findById(id);
        categoryRepository.delete(category);
        return categoryMapper.toDto(category);
    }
}


