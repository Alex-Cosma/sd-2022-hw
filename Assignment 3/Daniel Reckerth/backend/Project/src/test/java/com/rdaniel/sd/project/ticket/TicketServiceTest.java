package com.rdaniel.sd.project.ticket;

import com.rdaniel.sd.project.device.DeviceRepository;
import com.rdaniel.sd.project.device.dto.DeviceDto;
import com.rdaniel.sd.project.device.mapper.DeviceMapper;
import com.rdaniel.sd.project.device.model.Device;
import com.rdaniel.sd.project.ticket.dto.TicketDto;
import com.rdaniel.sd.project.ticket.mapper.TicketMapper;
import com.rdaniel.sd.project.ticket.model.Ticket;
import com.rdaniel.sd.project.ticket.model.enums.StatusType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.rdaniel.sd.project.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

  @InjectMocks
  private TicketService ticketService;

  @Mock
  private TicketRepository ticketRepository;

  @Mock
  private DeviceRepository deviceRepository;

  @Mock
  private TicketMapper ticketMapper;

  @Test
  void findAllByDeviceId() {
    final List<Ticket> tickets = listOf(Ticket.class, 10);
    final List<TicketDto> ticketsDto = TicketMapper.INSTANCE.toTicketDtoList(tickets);
    when(ticketRepository.findAllByDeviceId(any(Long.class))).thenReturn(tickets);
    when(ticketMapper.toTicketDtoList(tickets)).thenReturn(ticketsDto);

    final List<TicketDto> result = ticketService.findAllByDeviceId(1L);
    assertEquals(tickets.size(), result.size());

    verify(ticketRepository, times(1)).findAllByDeviceId(1L);
    verify(ticketMapper, times(1)).toTicketDtoList(tickets);
    verifyNoMoreInteractions(ticketRepository, ticketMapper);
  }

  @Test
  void findById() {
    final Ticket ticket = newTicket();
    final TicketDto ticketDto = TicketMapper.INSTANCE.toTicketDto(ticket);

    when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
    when(ticketMapper.toTicketDto(ticket)).thenReturn(ticketDto);

    final TicketDto result = ticketService.findById(ticket.getId());

    assertEquals(ticket.getId(), result.getId());
    assertEquals(ticket.getDescription(), result.getDescription());
    assertEquals(ticket.getStatus().toString(), result.getStatus());

    verify(ticketRepository, times(1)).findById(ticket.getId());
    verify(ticketMapper, times(1)).toTicketDto(ticket);
    verifyNoMoreInteractions(ticketRepository, ticketMapper);
  }

  @Test
  void create() {
    final Ticket ticket = newTicket();
    final TicketDto ticketDto = TicketMapper.INSTANCE.toTicketDto(ticket);
    final Device device = newDevice();

    when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
    when(ticketMapper.toTicket(ticketDto)).thenReturn(ticket);
    when(ticketRepository.save(ticket)).thenReturn(ticket);
    when(ticketMapper.toTicketDto(ticket)).thenReturn(ticketDto);

    final TicketDto result = ticketService.create(device.getId(), ticketDto);

    assertEquals(ticket.getId(), result.getId());
    assertEquals(ticket.getDescription(), result.getDescription());
    assertEquals(ticket.getStatus().toString(), result.getStatus());

    verify(deviceRepository, times(1)).findById(device.getId());
    verify(ticketMapper, times(1)).toTicket(ticketDto);
    verify(ticketRepository, times(1)).save(ticket);
    verify(ticketMapper, times(1)).toTicketDto(ticket);
    verifyNoMoreInteractions(deviceRepository, ticketMapper, ticketRepository);
  }

  @Test
  void update() {
    final Ticket ticket = newTicket();
    final TicketDto ticketDto = TicketMapper.INSTANCE.toTicketDto(ticket);
    ticketDto.setDescription("new description");

    when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
    ticket.setDescription("new description");
    when(ticketRepository.save(ticket)).thenReturn(ticket);
    when(ticketMapper.toTicketDto(ticket)).thenReturn(ticketDto);

    final TicketDto result = ticketService.update(ticket.getId(), ticketDto);

    assertEquals(ticket.getId(), result.getId());
    assertEquals(ticketDto.getDescription(), result.getDescription());
    assertEquals(ticket.getStatus().toString(), result.getStatus());

    verify(ticketRepository, times(1)).findById(ticket.getId());
    verify(ticketRepository, times(1)).save(ticket);
    verify(ticketMapper, times(1)).toTicketDto(ticket);
    verifyNoMoreInteractions(ticketRepository, ticketMapper);
  }

  @Test
  void changeStatus() {
    final Ticket ticket = newTicket();


    when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
    ticket.setStatus(StatusType.CLOSED);
    final TicketDto ticketDto = TicketMapper.INSTANCE.toTicketDto(ticket);
    when(ticketRepository.save(ticket)).thenReturn(ticket);
    when(ticketMapper.toTicketDto(ticket)).thenReturn(ticketDto);

    final TicketDto result = ticketService.changeStatus(ticket.getId(), StatusType.CLOSED);

    assertEquals(ticket.getId(), result.getId());
    assertEquals(ticketDto.getDescription(), result.getDescription());
    assertEquals(StatusType.CLOSED.toString(), result.getStatus());

    verify(ticketRepository, times(1)).findById(ticket.getId());
    verify(ticketRepository, times(1)).save(ticket);
    verify(ticketMapper, times(1)).toTicketDto(ticket);
    verifyNoMoreInteractions(ticketRepository, ticketMapper);
  }

  @Test
  void delete() {
    final Ticket ticket = newTicket();

    when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
    ticketService.delete(ticket.getId());

    verify(ticketRepository, times(1)).findById(ticket.getId());
    verify(ticketRepository, times(1)).deleteById(ticket.getId());
    verifyNoMoreInteractions(ticketRepository);
  }
}