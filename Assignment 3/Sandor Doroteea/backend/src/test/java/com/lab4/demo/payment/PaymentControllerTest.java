package com.lab4.demo.payment;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.payment.model.DTO.PaymentDTO;
import com.lab4.demo.payment.model.Payment;
import com.lab4.demo.report.ReportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.UrlMapping.ADD_PLAYLIST;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PaymentControllerTest extends BaseControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private  PaymentService paymentService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        paymentController = new PaymentController(paymentService);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void checkout() throws Exception {
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .name("Name")
                .track_id(1L)
                .user_id(1L)
                .build();

        ResultActions result = performPostWithRequestBody(PAYMENT, paymentDTO);
        result.andExpect(status().isOk());
    }

    @Test
    void export() throws Exception {
        when(paymentService.export(ReportType.CSV,1L)).thenReturn("src/main/resources/MyTracks.csv");
        FileInputStream file = new FileInputStream(paymentService.export(ReportType.CSV,1L));
        InputStreamResource inputStreamResource =  new InputStreamResource(file);

        ResultActions result = performGetWithPathVariable(PAYMENT + EXPORT_REPORT,ReportType.CSV,1L);
        result.andExpect(status().isOk());
    }

    @Test
    void findAll() throws Exception {
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .name("Name")
                .track_id(1L)
                .user_id(1L)
                .build();
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        paymentDTOList.add(paymentDTO);

        when(paymentService.findAll()).thenReturn(paymentDTOList);

        ResultActions result = performGet(PAYMENT);
        result.andExpect(status().isOk());
//                .andExpect(jsonContentToBe(paymentDTOList));
    }

    @Test
    void save() throws Exception {
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .id(1L)
                .name("Payment")
                .track_id(1L)
                .user_id(1L)
                .build();

        when(paymentService.savePayment(paymentDTO)).thenReturn(paymentDTO);

        ResultActions result = performPostWithRequestBody(PAYMENT,paymentDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(paymentDTO));
    }
}