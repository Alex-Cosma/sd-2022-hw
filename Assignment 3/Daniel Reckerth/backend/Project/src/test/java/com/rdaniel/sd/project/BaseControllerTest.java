package com.rdaniel.sd.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public abstract class BaseControllerTest {
  protected MockMvc mockMvc;

  @BeforeEach
  protected void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  protected ResultMatcher jsonContentToBe(Object expectedJsonContent) throws JsonProcessingException {
    try {
      return content().json(new ObjectMapper().writeValueAsString(expectedJsonContent), true);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  protected ResultActions performPostWithRequestBody(String path, Object body) throws Exception {
    return mockMvc.perform(jsonType(post(path)
        .content(asJsonString(body)))
    );
  }

  protected ResultActions performPostWithRequestBodyAndPathVariables(String path, Object body, Object... pathVariables) throws Exception {
    return mockMvc.perform(jsonType(post(path, pathVariables)
        .content(asJsonString(body)))
    );
  }

  protected ResultActions performPatchWithRequestBodyAndPathVariable(String path, Object body, Object pathVariable) throws Exception {
    return mockMvc.perform(jsonType(patch(path, pathVariable)
        .content(asJsonString(body)))
    );
  }

  protected ResultActions performPatchWithRequestBodyAndPathVariables(String path, Object body, Object... pathVariable) throws Exception {
    return mockMvc.perform(jsonType(patch(path, pathVariable)
        .content(asJsonString(body)))
    );
  }

  protected ResultActions performPutWithRequestBodyAndPathVariable(String path, Object body, Object pathVariable) throws Exception {
    return mockMvc.perform(jsonType(put(path, pathVariable)
        .content(asJsonString(body)))
    );
  }

  protected ResultActions performPutWithRequestBodyAndPathVariables(String path, Object body, Object... pathVariable) throws Exception {
    return mockMvc.perform(jsonType(put(path, pathVariable)
        .content(asJsonString(body)))
    );
  }

  protected ResultActions performDeleteWithPathVariable(String path, Object pathVariable) throws Exception {
    return mockMvc.perform(jsonType(delete(path, pathVariable)));
  }

  protected ResultActions performDeleteWithPathVariables(String path, Object... pathVariable) throws Exception {
    return mockMvc.perform(jsonType(delete(path, pathVariable)));
  }

  protected ResultActions performGetWithPathVariable(String path, Object pathVariable) throws Exception {
    return mockMvc.perform(jsonType(get(path, pathVariable)));
  }

  protected ResultActions performGetWithPathVariables(String path, Object... pathVariable) throws Exception {
    return mockMvc.perform(jsonType(get(path, pathVariable)));
  }

  protected ResultActions performGet(String path) throws Exception {
    return mockMvc.perform(jsonType(get(path)));
  }

  protected ResultActions performGetWithModelAttributeAndParams(String path, Pair<String, Object> modelAttribute,
                                                                List<Pair<String, String>> params) throws Exception {
    MockHttpServletRequestBuilder requestBuilder = get(path);
    params.forEach(param -> requestBuilder.param(param.getFirst(), param.getSecond()));
    requestBuilder.flashAttr(modelAttribute.getFirst(), modelAttribute.getSecond());
    return mockMvc.perform(jsonType(requestBuilder));
  }

  protected String asJsonString(final Object obj) {
    if (obj instanceof String) return String.valueOf(obj);
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected List<Pair<String, String>> pairsFromPagination(PageRequest pageRequest) {
    Sort.Order sort = pageRequest.getSort().stream().findFirst().orElseGet(() -> Sort.Order.by("name"));
    return List.of(
        Pair.of("page", String.valueOf(pageRequest.getPageNumber())),
        Pair.of("size", String.valueOf(pageRequest.getPageSize())),
        Pair.of("sort", (sort.getProperty() + "," + sort.getDirection().name()))
    );
  }

  private RequestBuilder jsonType(MockHttpServletRequestBuilder content) {
    return content.contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON);
  }
}
