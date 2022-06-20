package com.rdaniel.sd.project.device;

import com.rdaniel.sd.project.customer.CustomerRepository;
import com.rdaniel.sd.project.customer.model.Customer;
import com.rdaniel.sd.project.device.dto.DeviceDto;
import com.rdaniel.sd.project.device.mapper.DeviceMapper;
import com.rdaniel.sd.project.device.model.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.rdaniel.sd.project.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeviceServiceIntegrationTest {

  @Autowired
  private DeviceService deviceService;

  @Autowired
  private DeviceRepository deviceRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Test
  void findAllByCustomerId() {
    final List<Device> devices = listOf(Device.class, 5);
    final Customer customer = customerRepository.save(newCustomer());
    devices.forEach(device -> device.setCustomer(customer));

    deviceRepository.saveAll(devices);

    final List<DeviceDto> deviceDtos = deviceService.findAllByCustomerId(customer.getId());
    assertEquals(devices.size(), deviceDtos.size());
  }

  @Test
  void findById() {
    final Device saved = deviceRepository.save(newDevice());

    final DeviceDto deviceDto = deviceService.findById(saved.getId());

    assertEquals(saved.getId(), deviceDto.getId());
    assertEquals(saved.getName(), deviceDto.getName());
    assertEquals(saved.getBrand(), deviceDto.getBrand());
  }

  @Test
  void create() {
    final Customer customer = customerRepository.save(newCustomer());
    final DeviceDto deviceDto = newDeviceDto();

    final DeviceDto createdDeviceDto = deviceService.create(customer.getId(), deviceDto);

    assertEquals(deviceDto.getName(), createdDeviceDto.getName());
    assertEquals(deviceDto.getBrand(), createdDeviceDto.getBrand());
  }

  @Test
  void update() {
    final Device saved = deviceRepository.save(newDevice());
    final DeviceDto updatingDeviceDto = DeviceMapper.INSTANCE.toDeviceDto(saved);
    updatingDeviceDto.setName("Updated name");

    final DeviceDto updatedDeviceDto = deviceService.update(saved.getId(), updatingDeviceDto);

    assertEquals(updatingDeviceDto.getName(), updatedDeviceDto.getName());
    assertEquals(saved.getBrand(), updatedDeviceDto.getBrand());
  }

  @Test
  void delete() {
    final Device device = deviceRepository.save(newDevice());
    deviceService.delete(device.getId());

    final EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> {
      deviceService.findById(device.getId());
    });

    assertEquals("Device not found", entityNotFoundException.getMessage());
  }
}