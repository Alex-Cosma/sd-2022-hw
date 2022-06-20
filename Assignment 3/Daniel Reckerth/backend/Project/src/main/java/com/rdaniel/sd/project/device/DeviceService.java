package com.rdaniel.sd.project.device;

import com.rdaniel.sd.project.customer.CustomerRepository;
import com.rdaniel.sd.project.customer.model.Customer;
import com.rdaniel.sd.project.device.dto.DeviceDto;
import com.rdaniel.sd.project.device.mapper.DeviceMapper;
import com.rdaniel.sd.project.device.model.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {
  private final DeviceRepository deviceRepository;

  private final CustomerRepository customerRepository;

  private final DeviceMapper deviceMapper;

  public List<DeviceDto> findAllByCustomerId(Long customerId) {
    return deviceMapper.toDeviceDtoList(deviceRepository.findAllByCustomerId(customerId));
  }

  public List<DeviceDto> findAllByCustomerIdWithTickets(Long customerId) {
    return deviceMapper.toDeviceDtoList(deviceRepository.findAllByCustomerIdWithTickets(customerId));
  }

  public DeviceDto findById(Long id) {
    return deviceRepository.findById(id)
        .map(deviceMapper::toDeviceDto)
        .orElseThrow(() -> new EntityNotFoundException("Device not found"));
  }

  public DeviceDto create(Long customerId,  DeviceDto deviceDto) {
    final Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

    final Device device = deviceMapper.toDevice(deviceDto);
    device.setCustomer(customer);

    return deviceMapper.toDeviceDto(deviceRepository.save(device));
  }

  public DeviceDto update(Long id, DeviceDto deviceDto) {
    final Device device = deviceRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Device not found"));

    device.setName(deviceDto.getName());
    device.setBrand(deviceDto.getBrand());

    return deviceMapper.toDeviceDto(deviceRepository.save(device));
  }

  public void delete(Long id) {
    final Device device = deviceRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Device not found"));

    deviceRepository.deleteById(id);
  }

}
