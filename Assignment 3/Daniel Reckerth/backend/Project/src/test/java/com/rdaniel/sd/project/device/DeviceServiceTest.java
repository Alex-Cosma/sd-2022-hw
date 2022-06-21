package com.rdaniel.sd.project.device;

import com.rdaniel.sd.project.customer.CustomerRepository;
import com.rdaniel.sd.project.customer.model.Customer;
import com.rdaniel.sd.project.device.dto.DeviceDto;
import com.rdaniel.sd.project.device.mapper.DeviceMapper;
import com.rdaniel.sd.project.device.model.Device;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.rdaniel.sd.project.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

  @InjectMocks
  private DeviceService deviceService;

  @Mock
  private DeviceRepository deviceRepository;

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private DeviceMapper deviceMapper;


  @Test
  void findAllByCustomerId() {
    final List<Device> devices = listOf(Device.class, 10);
    final List<DeviceDto> devicesDtos = DeviceMapper.INSTANCE.toDeviceDtoList(devices);
    when(deviceRepository.findAllByCustomerId(any(Long.class))).thenReturn(devices);
    when(deviceMapper.toDeviceDtoList(devices)).thenReturn(devicesDtos);

    final List<DeviceDto> result = deviceService.findAllByCustomerId(1L);
    assertEquals(devices.size(), result.size());

    verify(deviceRepository, times(1)).findAllByCustomerId(1L);
    verify(deviceMapper, times(1)).toDeviceDtoList(devices);
    verifyNoMoreInteractions(deviceRepository, deviceMapper);
  }

  @Test
  void findById() {
    final Device device = newDevice();
    final DeviceDto deviceDto = DeviceMapper.INSTANCE.toDeviceDto(device);

    when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
    when(deviceMapper.toDeviceDto(device)).thenReturn(deviceDto);

    final DeviceDto result = deviceService.findById(device.getId());

    assertEquals(device.getId(), result.getId());
    assertEquals(device.getName(), result.getName());
    assertEquals(device.getBrand(), result.getBrand());

    verify(deviceRepository, times(1)).findById(device.getId());
    verify(deviceMapper, times(1)).toDeviceDto(device);
    verifyNoMoreInteractions(deviceRepository, deviceMapper);
  }

  @Test
  void create() {
    final Device device = newDevice();
    final Customer customer = newCustomer();
    final DeviceDto deviceDto = DeviceMapper.INSTANCE.toDeviceDto(device);

    when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
    when(deviceMapper.toDevice(deviceDto)).thenReturn(device);
    when(deviceRepository.save(device)).thenReturn(device);
    when(deviceMapper.toDeviceDto(device)).thenReturn(deviceDto);

    final DeviceDto result = deviceService.create(customer.getId(), deviceDto);

    assertEquals(device.getId(), result.getId());
    assertEquals(device.getName(), result.getName());
    assertEquals(device.getBrand(), result.getBrand());

    verify(customerRepository, times(1)).findById(customer.getId());
    verify(deviceMapper, times(1)).toDevice(deviceDto);
    verify(deviceRepository, times(1)).save(device);
    verify(deviceMapper, times(1)).toDeviceDto(device);
    verifyNoMoreInteractions(customerRepository, deviceMapper, deviceRepository);
  }

  @Test
  void update() {
    final Device device = newDevice();
    final DeviceDto deviceDto = DeviceMapper.INSTANCE.toDeviceDto(device);
    deviceDto.setBrand("newBrand");

    when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));

    device.setBrand("newBrand");
    when(deviceRepository.save(device)).thenReturn(device);
    when(deviceMapper.toDeviceDto(device)).thenReturn(deviceDto);

    final DeviceDto result = deviceService.update(device.getId(), deviceDto);

    assertEquals(device.getId(), result.getId());
    assertEquals(device.getName(), result.getName());
    assertEquals(deviceDto.getBrand(), result.getBrand());

    verify(deviceRepository, times(1)).findById(device.getId());
    verify(deviceRepository, times(1)).save(device);
    verify(deviceMapper, times(1)).toDeviceDto(device);
    verifyNoMoreInteractions(deviceRepository, deviceMapper);
  }

  @Test
  void delete() {
    final Device device = newDevice();
    when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));

    deviceService.delete(device.getId());

    verify(deviceRepository, times(1)).findById(device.getId());
    verify(deviceRepository, times(1)).deleteById(device.getId());
    verifyNoMoreInteractions(deviceRepository);
  }
}