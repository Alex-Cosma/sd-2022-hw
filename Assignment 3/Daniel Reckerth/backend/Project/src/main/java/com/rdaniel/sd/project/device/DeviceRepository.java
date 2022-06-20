package com.rdaniel.sd.project.device;

import com.rdaniel.sd.project.device.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

  List<Device> findAllByCustomerId(Long customerId);

  @Query("SELECT DISTINCT d FROM Device d INNER JOIN Ticket t ON t.device.id=d.id WHERE d.customer.id= ?1")
  List<Device> findAllByCustomerIdWithTickets(Long customerId);
}
