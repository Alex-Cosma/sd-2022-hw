package com.forum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public abstract class BaseControllerTest {
    protected MockMvc mockMvc;

    @BeforeEach
    protected void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    protected ResultMatcher jsonContentToBe(Object expectedJsonContent) throws JsonProcessingException {
        return content().json(new ObjectMapper().writeValueAsString(expectedJsonContent), true);
    }

    protected ResultActions performPostWithRequestBody(String path, Object body) throws Exception {
        return mockMvc.perform(post(path)
                .content(asJsonString(body))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performPutWithRequestBody(String path, Object body) throws Exception {
        return mockMvc.perform(put(path)
                .content(asJsonString(body))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performGet(String path) throws Exception {
        return mockMvc.perform(get(path));
    }

    protected ResultActions performPutWithRequestBodyAndPathVariables(String path, Object body, Object... pathVariables) throws Exception {
        return mockMvc.perform(put(path, pathVariables)
                .   content(asJsonString(body))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performGetWithRequestParam(String path, Object requestParams, List<Pair<String, String>> params) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(path, requestParams);
        params.forEach(param -> requestBuilder.param(param.getFirst(), param.getSecond()));
        return mockMvc.perform(requestBuilder);
    }

    protected ResultActions performGetWithRequestParams(String path, Object... requestParams) throws Exception {
        return mockMvc.perform(get(path, requestParams));
    }

    protected ResultActions performGetWithModelAttributeAndParams(String path, Pair<String, Object> modelAttribute,
                                                                  List<Pair<String, String>> params) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(path);
        params.forEach(param -> requestBuilder.param(param.getFirst(), param.getSecond()));
        requestBuilder.flashAttr(modelAttribute.getFirst(), modelAttribute.getSecond());
        return mockMvc.perform(requestBuilder);
    }

    protected String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected List<Pair<String, String>> pairsFromPagination(PageRequest pageRequest) {
        Sort.Order sort = pageRequest.getSort().stream().findFirst().orElseGet(() -> Sort.Order.by("title"));
        return List.of(
                Pair.of("page", String.valueOf(pageRequest.getPageNumber())),
                Pair.of("size", String.valueOf(pageRequest.getPageSize())),
                Pair.of("sort", (sort.getProperty() + "," + sort.getDirection().name()))
        );
    }

    protected ResultActions performDeleteWithPathVariables(String path, Object... pathVariables) throws Exception {
        return mockMvc.perform(delete(path, pathVariables));
    }

    protected ResultActions performPatchWithRequestBodyAndPathVariables(String path, Object body, Object... pathVariables) throws Exception {
        return mockMvc.perform(patch(path, pathVariables)
                .content(asJsonString(body))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performGetWithPathVariable(String path, Object pathVariable) throws Exception {
        return mockMvc.perform(get(path, pathVariable));
    }
}
