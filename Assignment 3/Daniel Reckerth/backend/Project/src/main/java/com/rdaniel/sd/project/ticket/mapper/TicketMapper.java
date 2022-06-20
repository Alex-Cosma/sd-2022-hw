package com.rdaniel.sd.project.ticket.mapper;

import com.rdaniel.sd.project.ticket.dto.TicketDto;
import com.rdaniel.sd.project.ticket.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

  TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

  TicketDto toTicketDto(Ticket ticket);

  Ticket toTicket(TicketDto ticketDto);

  List<TicketDto> toTicketDtoList(List<Ticket> tickets);
}
