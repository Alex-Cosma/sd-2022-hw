package com.rdaniel.sd.project.ticket;

import com.rdaniel.sd.project.common.websocket.WebSocketService;
import com.rdaniel.sd.project.device.DeviceRepository;
import com.rdaniel.sd.project.device.model.Device;
import com.rdaniel.sd.project.ticket.dto.TicketDto;
import com.rdaniel.sd.project.ticket.mapper.TicketMapper;
import com.rdaniel.sd.project.ticket.model.Ticket;
import com.rdaniel.sd.project.ticket.model.enums.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.rdaniel.sd.project.ticket.model.enums.StatusType.*;
import static java.lang.String.valueOf;

@Service
@RequiredArgsConstructor
public class TicketService {
  private final TicketRepository ticketRepository;
  private final TicketMapper ticketMapper;
  private final DeviceRepository deviceRepository;

  private final WebSocketService webSocketService;

  public List<TicketDto> findAllByDeviceId(Long deviceId) {
    return ticketMapper.toTicketDtoList(ticketRepository.findAllByDeviceId(deviceId));
  }

  public TicketDto findById(Long id) {
    return ticketRepository.findById(id)
        .map(ticketMapper::toTicketDto)
        .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
  }

  public TicketDto create(Long deviceId, TicketDto ticketDto) {
    final Device device = deviceRepository.findById(deviceId)
        .orElseThrow(() -> new EntityNotFoundException("Device not found"));

    final Ticket ticket = ticketMapper.toTicket(ticketDto);
    ticket.setDevice(device);

    return ticketMapper.toTicketDto(ticketRepository.save(ticket));
  }

  public TicketDto update(Long id, TicketDto ticketDto) {
    final Ticket ticket = ticketRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

    ticket.setDescription(ticketDto.getDescription());

    return ticketMapper.toTicketDto(ticketRepository.save(ticket));
  }

  public TicketDto changeStatus(Long id, StatusType statusType) {
    final Ticket ticket = ticketRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

    ticket.setStatus(statusType);
    if(statusType == OPEN) {
      webSocketService.sendMessage(String.valueOf(ticket.getDevice().getId()), "One of your ticket was opened for " + ticket.getDevice().getName());
    }


    return ticketMapper.toTicketDto(ticketRepository.save(ticket));
  }

  public void delete(Long id) {
    final Ticket ticket = ticketRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

    ticketRepository.deleteById(ticket.getId());
  }
}
