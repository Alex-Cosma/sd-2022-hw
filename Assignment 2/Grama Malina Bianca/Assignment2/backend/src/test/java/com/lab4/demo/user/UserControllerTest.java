package com.lab4.demo.user;

import com.lab4.demo.report.CSVReportService;
import com.lab4.demo.report.PDFReportService;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PDFReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

//    @Test
//    void create() throws Exception {
//        UserListDTO user = UserListDTO.builder()
//                .name(TestCreationFactory.randomString())
//                .email(TestCreationFactory.randomEmail())
//                .password(TestCreationFactory.randomString())
//                .build();
//
//        // when(userService.create(user)).thenReturn(response);
//
//        ResultActions result = performPostWithRequestBody(USERS, user);
//        result.andExpect(status().isOk()).andExpect(jsonContentToBe(MessageResponse.class));
//    }

//    @Test
//    void edit() throws Exception {
//        long id = TestCreationFactory.randomLong();
//        UserListDTO user = UserListDTO.builder()
//                .id(id)
//                .name(TestCreationFactory.randomString())
//                .email(TestCreationFactory.randomEmail())
//                .password(TestCreationFactory.randomString())
//                .roles(Set.of("EMPLOYEE"))
//                .build();
//
//        user.setName("newName");
//
//        when(userService.edit(id, user)).thenReturn(user);
//
//        String path = USERS+"/"+id;
//        System.out.println(path);
//        ResultActions result = performPutWithRequestBodyAndPathVariable(USERS + "/" + id, user, id);
//        result.andExpect(status().isOk()).andExpect(jsonContentToBe(user));
//    }

    @Test
    void exportReport() throws Exception {
        when(reportServiceFactory.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);
        String pdfResponse = "Exported to pdf-report.pdf";
        when(pdfReportService.export()).thenReturn(pdfResponse);
        String csvResponse = "Exported to csv-report.csv";
        when(csvReportService.export()).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(get(USERS + EXPORT_REPORT, "pdf"));
        ResultActions csvExport = mockMvc.perform(get(USERS + EXPORT_REPORT, "csv"));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }
}