package com.example.airbnb.accommodation;

import com.example.airbnb.TestCreationFactory;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import com.example.airbnb.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.List;

import static com.example.airbnb.user.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccommodationControllerTest extends com.example.bookstore.BaseControllerTest {
    @InjectMocks
    private AccommodationController accommodationController;

    @Mock
    private AccommodationService accommodationService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        accommodationController = new AccommodationController(accommodationService);
        mockMvc = MockMvcBuilders.standaloneSetup(accommodationController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getAccommodations() throws Exception {

        List<Accommodation> accommodations = TestCreationFactory.listOf(Accommodation.class);
        final Page<Accommodation> accommodationPages = new PageImpl<>(accommodations);
        when(accommodationService.findAll()).thenReturn(accommodationPages);

        ResultActions result = performGet(ACCOMMODATIONS);

        verify(accommodationService, times(1)).findAll();
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(accommodationPages));

    }

    @Test
    void getAccommodation() throws Exception {

        Accommodation accommodation = TestCreationFactory.accommodation();
        when(accommodationService.findById(accommodation.getId())).thenReturn(accommodation);

        ResultActions result = performGetWithPathVariable(ACCOMMODATIONS + ENTITY, accommodation.getId());

        verify(accommodationService, times(1)).findById(accommodation.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(accommodation));

    }

    @Test
    void delete() throws Exception {
        Accommodation accommodation = TestCreationFactory.accommodation();
        doNothing().when(accommodationService).delete(accommodation.getId());

        ResultActions result = performDeleteWithPathVariable(ACCOMMODATIONS + ENTITY, accommodation.getId());

        verify(accommodationService, times(1)).delete(accommodation.getId());
        result.andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        AccommodationDTO accommodationDTO = TestCreationFactory.accommodationDTO();

        when(accommodationService.create(accommodationDTO)).thenReturn(accommodationDTO);

        ResultActions result = performPostWithRequestBody(ACCOMMODATIONS, accommodationDTO);
        verify(accommodationService, times(1)).create(accommodationDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(accommodationDTO));

    }

    @Test
    void edit() throws Exception {
        AccommodationDTO accommodationDTO = TestCreationFactory.accommodationDTO();
        accommodationDTO.setId(TestCreationFactory.randomLong());

        when(accommodationService.edit(accommodationDTO.getId(), accommodationDTO)).thenReturn(accommodationDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariable(ACCOMMODATIONS + ENTITY, accommodationDTO, accommodationDTO.getId());
        verify(accommodationService, times(1)).edit(accommodationDTO.getId(), accommodationDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(accommodationDTO));
    }

    @Test
    void exportPDF() throws Exception {
        Accommodation accommodation = TestCreationFactory.accommodation();
        ResponseEntity<byte[]> response = new ResponseEntity<>(HttpStatus.OK);
//        doNothing().when(accommodationService).exportPDF(accommodation.getId());
        when(accommodationService.exportPDF(accommodation.getId())).thenReturn(response);

        ResultActions result = performGetWithPathVariable(ACCOMMODATIONS + EXPORT_ACCOMMODATION_PDF, accommodation.getId());
        verify(accommodationService, times(1)).exportPDF(accommodation.getId());
        result.andExpect(status().isOk());
    }

    @Test
    void sendEmail() throws Exception {
        doNothing().when(accommodationService).sendEmail(anyLong());

        ResultActions result = performGetWithPathVariable(ACCOMMODATIONS + SEND_EMAIL, TestCreationFactory.randomLong());
        verify(accommodationService, times(1)).sendEmail(anyLong());
        result.andExpect(status().isOk());
    }
}