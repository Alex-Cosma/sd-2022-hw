package com.rdaniel.sd.project.ticket;

import com.rdaniel.sd.project.common.websocket.WebSocketService;
import com.rdaniel.sd.project.device.DeviceRepository;
import com.rdaniel.sd.project.device.model.Device;
import com.rdaniel.sd.project.ticket.dto.TicketDto;
import com.rdaniel.sd.project.ticket.mapper.TicketMapper;
import com.rdaniel.sd.project.ticket.model.Ticket;
import com.rdaniel.sd.project.ticket.model.enums.StatusType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.rdaniel.sd.project.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TicketServiceIntegrationTest {

  @Autowired
  private TicketService ticketService;

  @Autowired
  private TicketRepository ticketRepository;

  @Autowired
  private DeviceRepository deviceRepository;

  @Autowired
  private WebSocketService webSocketService;

  @Test
  void findAllByDeviceId() {
    final List<Ticket> tickets = listOf(Ticket.class, 5);
    final Device device = deviceRepository.save(newDevice());
    tickets.forEach(ticket -> ticket.setDevice(device));

    ticketRepository.saveAll(tickets);

    final List<TicketDto> ticketsDto = ticketService.findAllByDeviceId(device.getId());

    assertEquals(tickets.size(), ticketsDto.size());
  }

  @Test
  void findById() {
    final Ticket saved = ticketRepository.save(newTicket());
    final TicketDto ticketDto = ticketService.findById(saved.getId());

    assertEquals(saved.getId(), ticketDto.getId());
    assertEquals(saved.getDescription(), ticketDto.getDescription());
    assertEquals(saved.getStatus().toString(), ticketDto.getStatus());
  }

  @Test
  void create() {
    final Device device = deviceRepository.save(newDevice());
    final TicketDto ticketDto = newTicketDto();

    final TicketDto createdTicketDto = ticketService.create(device.getId(), ticketDto);

    assertEquals(ticketDto.getDescription(), createdTicketDto.getDescription());
    assertEquals(ticketDto.getStatus(), createdTicketDto.getStatus());
  }

  @Test
  void update() {
    final Ticket saved = ticketRepository.save(newTicket());
    final TicketDto ticketDto = TicketMapper.INSTANCE.toTicketDto(saved);
    ticketDto.setDescription("Updated description");

    final TicketDto updatedTicketDto = ticketService.update(saved.getId(), ticketDto);

    assertEquals(ticketDto.getDescription(), updatedTicketDto.getDescription());
    assertEquals(saved.getStatus().toString(), updatedTicketDto.getStatus());
  }

  @Test
  void changeStatus() {
    final Ticket saved = ticketRepository.save(newTicket());

    final TicketDto updatedTicketDto = ticketService.changeStatus(saved.getId(), StatusType.CLOSED);

    assertEquals(StatusType.CLOSED.toString(), updatedTicketDto.getStatus());

  }

  @Test
  void delete() {
    final Ticket ticket = ticketRepository.save(newTicket());
    ticketService.delete(ticket.getId());

    assertFalse(ticketRepository.findById(ticket.getId()).isPresent());
  }
}