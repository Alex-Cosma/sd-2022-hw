package com.lab4.demo.order;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.order.dto.OrderDTO;
import com.lab4.demo.order.service.OrderService;
import com.lab4.demo.report.PDFReportService;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends BaseControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private PDFReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        orderController = new OrderController(orderService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void create() throws Exception {
        OrderDTO orderDTO = TestCreationFactory.newOrderDTO();
        when(orderService.create(orderDTO)).thenReturn(orderDTO);

        ResultActions result = performPostWithRequestBody(ORDERS, orderDTO);
        result.andExpect(status().isOk())
            .andExpect(jsonContentToBe(orderDTO));
    }

    @Test
    void allOrdersForUser() throws Exception {
        List<OrderDTO> orderDTOS = TestCreationFactory.listOf(OrderDTO.class);
        User user = TestCreationFactory.newUser();

        List<OrderDTO> expected = new ArrayList<>();
        for(int i = 0; i < orderDTOS.size(); i++) {
            if(i % 3 == 0) {
                orderDTOS.get(i).setUserId(user.getId());
                expected.add(orderDTOS.get(i));
            }
        }

        when(orderService.findAllForUser(user.getId())).thenReturn(expected);

        ResultActions result = performGetWithPathVariable(ORDERS + ENTITY, user.getId());
        result.andExpect(status().isOk())
            .andExpect(jsonContentToBe(expected));
    }

    @Test
    void delete() throws Exception {
        Long id = TestCreationFactory.randomLong();
        doNothing().when(orderService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(ORDERS + ENTITY, id);
        verify(orderService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }

    @Test
    void report() throws Exception {
        String pathFile = "C:\\UNI\\AN 3 - SEM 2\\SD\\sd-2022-hw\\Assignment 3\\Grama Malina Bianca\\SD-proj";
        byte[] contents = Files.readAllBytes(Paths.get(pathFile + "\\pdfBoxHelloWorld.pdf"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "pdfBoxHelloWorld.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(contents, headers, HttpStatus.OK);

        User user = TestCreationFactory.newUser();
        Long id = user.getId();
        String reportType = "pdf";

        when(reportServiceFactory.getReportService(ReportType.PDF)).thenReturn(pdfReportService);
        when(pdfReportService.export(id)).thenReturn(responseEntity);

        ResultActions result = performGetWithPathVariables(ORDERS + REPORT, reportType, id);

        verify(pdfReportService, times(1)).export(id);

        result.andExpect(status().isOk());
    }
}