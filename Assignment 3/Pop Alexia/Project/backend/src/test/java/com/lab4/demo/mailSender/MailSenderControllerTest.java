package com.lab4.demo.mailSender;

import com.lab4.demo.BaseControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.lab4.demo.UrlMapping.EMAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MailSenderControllerTest extends BaseControllerTest {

    @InjectMocks
    private EmailController controller;

    @Mock
    private EmailService emailService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new EmailController(emailService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void sendMail() throws Exception {
        ResultActions response = performGet(EMAIL);
        response.andExpect(status().isOk());
    }

}
