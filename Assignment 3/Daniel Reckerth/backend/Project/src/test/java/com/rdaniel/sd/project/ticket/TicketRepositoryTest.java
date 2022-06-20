package com.rdaniel.sd.project.ticket;

import com.rdaniel.sd.project.device.DeviceRepository;
import com.rdaniel.sd.project.device.model.Device;
import com.rdaniel.sd.project.ticket.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.rdaniel.sd.project.TestCreationFactory.listOf;
import static com.rdaniel.sd.project.TestCreationFactory.newDevice;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TicketRepositoryTest {

  @Autowired
  private TicketRepository ticketRepository;

  @Autowired
  private DeviceRepository deviceRepository;

  @Test
  void findAllByDeviceId() {
    final List<Ticket> tickets = listOf(Ticket.class, 5);
    final Device device = deviceRepository.save(newDevice());
    tickets.forEach(ticket -> ticket.setDevice(device));
    ticketRepository.saveAll(tickets);

    final List<Ticket> byDeviceId = ticketRepository.findAllByDeviceId(device.getId());

    assertEquals(tickets.size(), byDeviceId.size());
  }
}