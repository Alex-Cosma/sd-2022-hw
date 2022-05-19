package com.lab4.demo.webSocket;

import com.lab4.demo.BaseControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class WebSocketControllerTest extends BaseControllerTest{

    @InjectMocks
    private WebSocketController controller;


    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new WebSocketController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

//    @Test
//    void sendMessage() throws Exception {
//        RequestMessage requestMessage = new RequestMessage("Hello");
//        ResponseMessage responseMessage = new ResponseMessage("Hello back!");
//
//        when(controller.sendMessage(requestMessage)).thenReturn(responseMessage);
//
//    }
}
