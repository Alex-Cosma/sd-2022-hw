package com.rdaniel.sd.project.ticket;


import com.rdaniel.sd.project.BaseControllerTest;
import com.rdaniel.sd.project.ticket.dto.TicketDto;
import com.rdaniel.sd.project.ticket.model.enums.StatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.rdaniel.sd.project.TestCreationFactory.listOf;
import static com.rdaniel.sd.project.TestCreationFactory.newTicketDto;
import static com.rdaniel.sd.project.UrlMappings.TICKETS_PATH;
import static com.rdaniel.sd.project.UrlMappings.TICKET_ID_PATH;
import static com.rdaniel.sd.project.ticket.model.enums.StatusType.CLOSED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TicketControllerTest extends BaseControllerTest {

  @InjectMocks
  private TicketController ticketController;

  @Mock
  private TicketService ticketService;

  @BeforeEach
  protected void setUp() {
    super.setUp();
    ticketController = new TicketController(ticketService);
    mockMvc = MockMvcBuilders.standaloneSetup(ticketController)
        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        .build();
  }

  @Test
  void getAllDeviceTickets_returnedList_status200() throws Exception {
    List<TicketDto> tickets = listOf(TicketDto.class, 5);
    when(ticketService.findAllByDeviceId(any(Long.class))).thenReturn(tickets);

    ResultActions response = performGetWithPathVariables(TICKETS_PATH, 1L, 1L);
    response.andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonContentToBe(tickets));

    verify(ticketService, times(1)).findAllByDeviceId(any(Long.class));
  }

  @Test
  void getTicket_validIdentifier_status200() throws Exception {
    final TicketDto ticketDto = newTicketDto();
    when(ticketService.findById(any(Long.class))).thenReturn(ticketDto);

    ResultActions response = performGetWithPathVariables(TICKETS_PATH + TICKET_ID_PATH, 1L, 1L, 1L);
    response.andExpect(status().isOk())
        .andExpect(jsonContentToBe(ticketDto));

    verify(ticketService, times(1)).findById(any(Long.class));
    verifyNoMoreInteractions(ticketService);
  }

  @Test
  void getTicket_invalidIdentifier_status404() throws Exception {
    when(ticketService.findById(any(Long.class))).thenThrow(EntityNotFoundException.class);

    ResultActions response = performGetWithPathVariables(TICKETS_PATH + TICKET_ID_PATH, 1L, 1L, 1L);
    response.andExpect(status().isNotFound());

    verify(ticketService, times(1)).findById(any(Long.class));
  }

  @Test
  void createDevice_validData_status201() throws Exception {
    final TicketDto ticketDto = newTicketDto();
    when(ticketService.create(any(Long.class), any(TicketDto.class))).thenReturn(ticketDto);

    ResultActions response = performPostWithRequestBodyAndPathVariables(TICKETS_PATH, ticketDto, 1L, 1L);
    response.andExpect(status().isCreated())
        .andExpect(jsonContentToBe(ticketDto));

    verify(ticketService, times(1)).create(any(Long.class), any(TicketDto.class));
  }

  @Test
  void updateDevice_validIdentifierAndContent_status200() throws Exception {
    final TicketDto ticketDto = newTicketDto();
    when(ticketService.update(any(Long.class), any(TicketDto.class))).thenReturn(ticketDto);

    final ResultActions resultActions = performPutWithRequestBodyAndPathVariables(TICKETS_PATH + TICKET_ID_PATH, ticketDto, 1L, 1L, 1L);
    resultActions.andExpect(status().isOk())
        .andExpect(jsonContentToBe(ticketDto));

    verify(ticketService, times(1)).update(any(Long.class), any(TicketDto.class));
  }

  @Test
  void updateDevice_invalidIdentifier_status404() throws Exception {
    final TicketDto ticketDto = newTicketDto();
    when(ticketService.update(any(Long.class), any(TicketDto.class))).thenThrow(EntityNotFoundException.class);

    final ResultActions resultActions = performPutWithRequestBodyAndPathVariables(TICKETS_PATH + TICKET_ID_PATH, ticketDto, 1L, 1L, 1L);
    resultActions.andExpect(status().isNotFound());

    verify(ticketService, times(1)).update(any(Long.class), any(TicketDto.class));
  }

  @Test
  void changeTicketStatus_validIdentifier_status200() throws Exception {
    final TicketDto ticketDto = newTicketDto();
    ticketDto.setStatus(CLOSED.toString());
    when(ticketService.changeStatus(any(Long.class), any(StatusType.class))).thenReturn(ticketDto);

    final ResultActions resultActions = performPatchWithRequestBodyAndPathVariables(TICKETS_PATH + TICKET_ID_PATH, CLOSED, 1L, 1L, 1L);
    resultActions.andExpect(status().isOk())
        .andExpect(jsonContentToBe(ticketDto));

    verify(ticketService, times(1)).changeStatus(any(Long.class), any(StatusType.class));
  }

  @Test
  void deleteDevice_validIdentifier_status204() throws Exception {
    mockMvc.perform(delete(TICKETS_PATH + TICKET_ID_PATH, 1L, 1L, 1L))
        .andExpect(status().isNoContent());

    verify(ticketService, times(1)).delete(any(Long.class));
  }

  @Test
  void deleteDevice_invalidIdentifier_status404() throws Exception {
    doThrow(EntityNotFoundException.class).when(ticketService).delete(any(Long.class));

    final ResultActions resultActions = performDeleteWithPathVariables(TICKETS_PATH + TICKET_ID_PATH, 1L, 1L, 1L);
    resultActions.andExpect(status().isNotFound());

    verify(ticketService, times(1)).delete(any(Long.class));
  }

}