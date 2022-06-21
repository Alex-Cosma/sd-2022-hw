package com.rdaniel.sd.project.device.mapper;

import com.rdaniel.sd.project.device.dto.DeviceDto;
import com.rdaniel.sd.project.device.model.Device;
import com.rdaniel.sd.project.ticket.mapper.TicketMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

  DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

  DeviceDto toDeviceDto(Device device);

  Device toDevice(DeviceDto deviceDto);

  List<DeviceDto> toDeviceDtoList(List<Device> devices);
}
