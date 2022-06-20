package com.rdaniel.sd.project.ticket;

import com.rdaniel.sd.project.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

  List<Ticket> findAllByDeviceId(Long deviceId);
}
