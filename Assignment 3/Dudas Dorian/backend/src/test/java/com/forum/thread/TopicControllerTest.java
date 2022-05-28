package com.forum.thread;

import com.forum.BaseControllerTest;
import com.forum.category.model.dto.CategoryDTO;
import com.forum.thread.model.dto.TopicDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.forum.TestCreationFactory.*;
import static com.forum.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TopicControllerTest extends BaseControllerTest {
    @InjectMocks
    private TopicController topicController;

    @Mock
    private TopicService topicService;

    @BeforeEach
    public void setUp() {
        super.setUp();
        topicController = new TopicController(topicService);
        mockMvc = MockMvcBuilders.standaloneSetup(topicController).build();
    }

    @Test
    void allItems() throws Exception {
        List<TopicDTO> topics = listOf(TopicDTO.class);
        when(topicService.findAll()).thenReturn(topics);

        ResultActions response = performGet(THREAD);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(topics));
    }

    @Test
    void getById() throws Exception {
        TopicDTO topic = newTopicDTO();
        when(topicService.findTopicById(topic.getId())).thenReturn(topic);

        ResultActions response = performGetWithPathVariable(THREAD + THREAD_ID_PART, topic.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(topic));
    }

    @Test
    void getByCategory() throws Exception {
        CategoryDTO category = newCategoryDTO();
        List<TopicDTO> topics = listOf(TopicDTO.class);
        when(topicService.findAllByCategoryId(category.getId())).thenReturn(topics);

        ResultActions response = performGetWithPathVariable(THREAD + OF_CATEGORY_ID_PART, category.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(topics));
    }

    @Test
    void create() throws Exception {
        TopicDTO topic = newTopicDTO();
        when(topicService.create(topic)).thenReturn(topic);

        ResultActions response = performPostWithRequestBody(THREAD, topic);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(topic));
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        TopicDTO topic = newTopicDTO();

        when(topicService.edit(id, topic)).thenReturn(topic);

        ResultActions response = performPutWithRequestBodyAndPathVariables(THREAD + THREAD_ID_PART, topic, id);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(topic));
    }

    @Test
    void delete() throws Exception {
        final TopicDTO topic = newTopicDTO();
        when(topicService.delete(topic.getId())).thenReturn(topic);

        ResultActions response = performDeleteWithPathVariables(THREAD + THREAD_ID_PART, topic.getId());

        response.andExpect(status().isOk());
    }
}
