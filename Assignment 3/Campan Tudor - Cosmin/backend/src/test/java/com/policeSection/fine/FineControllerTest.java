package com.policeSection.fine;

import com.policeSection.BaseControllerTest;
import com.policeSection.fine.Model.Fine;
import com.policeSection.fine.Model.dto.FineDTO;
import com.policeSection.security.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.policeSection.UrlMapping.CREATE_FINE;
import static com.policeSection.UrlMapping.FINES;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class FineControllerTest extends BaseControllerTest {

    @InjectMocks
    private FineController controller;

    @Mock
    private FineService fineService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new FineController(fineService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allItems() throws Exception {
        FineDTO fine1=FineDTO.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();
        FineDTO fine2=FineDTO.builder()
                .id(2L)
                .type("random")
                .description("random")
                .price(200)
                .build();
        List<FineDTO> list =new ArrayList<>();
        list.add(fine1);
        list.add(fine2);
        when(fineService.findAll()).thenReturn(list);
        ResultActions response = performGet(FINES);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(list));
    }
    @Test
    void create() throws Exception {
        FineDTO fine1=FineDTO.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();

        when(fineService.create(fine1)).thenReturn(fine1);

        ResultActions result = performPostWithRequestBody(FINES+CREATE_FINE, fine1);
        result.andExpect(status().isOk());
    }
    @Test
    void edit() throws Exception
    {
        FineDTO fine1=FineDTO.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();

        when(fineService.create(fine1)).thenReturn(fine1);

        ResultActions result = performPostWithRequestBody(FINES+CREATE_FINE, fine1);
        result.andExpect(status().isOk());
        fine1.setType("random test");
        when(fineService.edit(fine1.getId(),fine1)).thenReturn(fine1);
        ResultActions result2 = performPutWithRequestBodyAndPathVariables(FINES+"/{id}", fine1,fine1.getId());
        result2.andExpect(status().isOk());
    }
    @Test
    void delete() throws Exception
    {
        FineDTO fine1=FineDTO.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();

        when(fineService.create(fine1)).thenReturn(fine1);

        ResultActions result = performPostWithRequestBody(FINES+CREATE_FINE, fine1);
        result.andExpect(status().isOk());
        doNothing().when(fineService).delete(fine1.getId());

        ResultActions result2 = performDeleteWIthPathVariable(FINES +"/{id}", fine1.getId());
        result2.andExpect(status().isOk());
        verify(fineService, times(1)).delete(fine1.getId());
    }


}