package com.app.bookingapp.controller;

import com.app.bookingapp.BaseControllerTest;
import com.app.bookingapp.TestCreationFactory;
import com.app.bookingapp.data.dto.model.PropertyDto;
import com.app.bookingapp.data.sql.entity.User;
import com.app.bookingapp.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.app.bookingapp.TestCreationFactory.buildUser;
import static com.app.bookingapp.TestCreationFactory.randomLong;
import static com.app.bookingapp.utils.URLMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PropertyControllerTest extends BaseControllerTest {
    @InjectMocks
    private PropertyController propertyController;

    @Mock
    private PropertyService propertyService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        propertyController = new PropertyController(propertyService);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).build();
    }

    @Test
    void testAllBooks() throws Exception {
        List<PropertyDto> properties = TestCreationFactory.listOf(PropertyDto.class);
        when(propertyService.findAll()).thenReturn(properties);

        ResultActions response = performGet(PROPERTY);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(properties));

    }

    @Test
    void testAllPropertiesByOwner() throws Exception {
        User user = buildUser();
        List<PropertyDto> properties = TestCreationFactory.listOf(PropertyDto.class, user);
        when(propertyService.allPropertiesByOwner(user.getUsername())).thenReturn(properties);

        ResultActions response = performGetWithPathVariable(PROPERTY + USERNAME, user.getUsername());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(properties));
    }

    @Test
    void testCreate() throws Exception {                        //TODO
        PropertyDto reqProperty = TestCreationFactory.newPropertyDto();

        when(propertyService.create(reqProperty)).thenReturn(reqProperty);

        ResultActions result = performPostWithRequestBody(PROPERTY, reqProperty);

        verify(propertyService, times(1)).create(reqProperty);

        ResultMatcher jsonReqItem = jsonContentToBe(reqProperty);
        result.andExpect(status().isCreated());
        MvcResult mvcResult = result.andReturn();
        jsonReqItem.match(mvcResult);
    }

    @Test
    void testUpdate() throws Exception {                //TODO
        final long id = randomLong();
        PropertyDto reqProperty = TestCreationFactory.newPropertyDto();

        when(propertyService.update(id, reqProperty)).thenReturn(reqProperty);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(PROPERTY + ID, id, reqProperty);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqProperty));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();

        doNothing().when(propertyService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(PROPERTY + ID, id);

        result.andExpect(status().isOk());
    }

}
