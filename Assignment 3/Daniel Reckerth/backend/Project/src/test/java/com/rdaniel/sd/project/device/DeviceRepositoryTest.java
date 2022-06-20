package com.rdaniel.sd.project.device;

import com.rdaniel.sd.project.customer.CustomerRepository;
import com.rdaniel.sd.project.customer.model.Customer;
import com.rdaniel.sd.project.device.model.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.rdaniel.sd.project.TestCreationFactory.listOf;
import static com.rdaniel.sd.project.TestCreationFactory.newCustomer;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeviceRepositoryTest {

  @Autowired
  private DeviceRepository deviceRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Test
  void findAllByCustomerId() {
    final List<Device> devices = listOf(Device.class, 5);
    final Customer customer = newCustomer();
    final Customer saved = customerRepository.save(customer);
    devices.forEach(device -> device.setCustomer(saved));
    deviceRepository.saveAll(devices);

    final List<Device> byCustomerId = deviceRepository.findAllByCustomerId(saved.getId());

    assertEquals(devices.size(), byCustomerId.size());
  }
}