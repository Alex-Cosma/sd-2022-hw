package com.forum.thread;

import com.forum.thread.model.dto.TopicDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.forum.UrlMapping.*;

@RestController
@RequestMapping(THREAD)
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @GetMapping
    public List<TopicDTO> allTopics() {
        return topicService.findAll();
    }

    @GetMapping(OF_CATEGORY_ID_PART)
    public List<TopicDTO> allTopicsOfCategory(@PathVariable("id") Long categoryId) {
        return topicService.findAllByCategoryId(categoryId);
    }

    @GetMapping(THREAD_ID_PART)
    public TopicDTO topicById(@PathVariable("id") Long id) {
        return topicService.findTopicById(id);
    }

    @PostMapping
    public TopicDTO create(@RequestBody TopicDTO topic) {
        return topicService.create(topic);
    }

    @PutMapping(THREAD_ID_PART)
    public TopicDTO edit(@PathVariable Long id, @RequestBody TopicDTO topic) {
        return topicService.edit(id, topic);
    }

    @DeleteMapping(THREAD_ID_PART)
    public TopicDTO delete(@PathVariable Long id) {
        return topicService.delete(id);
    }
}
