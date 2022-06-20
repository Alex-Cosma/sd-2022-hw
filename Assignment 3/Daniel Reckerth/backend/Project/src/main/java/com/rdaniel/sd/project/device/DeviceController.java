package com.rdaniel.sd.project.device;

import com.rdaniel.sd.project.device.dto.DeviceDto;
import com.rdaniel.sd.project.ticket.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.rdaniel.sd.project.UrlMappings.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping(DEVICES_PATH)
@RequiredArgsConstructor
@Api("Set of endpoints for managing customer's devices")
public class DeviceController {

  private final DeviceService deviceService;

  @ApiOperation(httpMethod = "GET", value = "Get customer's devices")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Devices retrieved", response =DeviceDto.class),
      @ApiResponse(code = 404, message = "Customer not found")
  })
  @GetMapping
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('CUSTOMER')")
  public List<DeviceDto> findAllCustomerDevices(@PathVariable("id") Long id) {
    try {
      return deviceService.findAllByCustomerId(id);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Get customer's devices wich have tickets")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Devices retrieved", response =DeviceDto.class),
      @ApiResponse(code = 404, message = "Customer not found")
  })
  @GetMapping(DEVICES_WITH_TICKETS)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
  public List<DeviceDto> findAllCustomerDevicesWithTickets(@PathVariable("id") Long id) {
    try {
      return deviceService.findAllByCustomerIdWithTickets(id);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Get device by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Device returned", response = DeviceDto.class),
      @ApiResponse(code = 404, message = "Device not found")
  })
  @GetMapping(DEVICE_ID_PATH)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
  public DeviceDto findDevice(@PathVariable(value = "id", required = false) String customerId, @PathVariable("deviceId") Long deviceId) {
    try {
      return deviceService.findById(deviceId);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "POST", value = "Create device for customer")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Device created", response = DeviceDto.class),
      @ApiResponse(code = 404, message = "Customer not found")
  })
  @PostMapping
  @ResponseStatus(CREATED)
  @PreAuthorize("hasRole('CUSTOMER')")
  public DeviceDto createDevice(@PathVariable("id") Long customerId, @RequestBody DeviceDto deviceDto) {
    try {
      return deviceService.create(customerId, deviceDto);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "PUT", value = "Update device by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Device updated", response = DeviceDto.class),
      @ApiResponse(code = 404, message = "Device not found")
  })
  @PutMapping(DEVICE_ID_PATH)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('CUSTOMER')")
  public DeviceDto updateDevice(@PathVariable(value = "id", required = false) Long customerId, @PathVariable("deviceId") Long deviceId, @RequestBody DeviceDto deviceDto) {
    try {
      return deviceService.update(deviceId, deviceDto);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }


  @ApiOperation(httpMethod = "DELETE", value = "Delete device by id")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Device deleted"),
      @ApiResponse(code = 404, message = "Device not found")
  })
  @DeleteMapping(DEVICE_ID_PATH)
  @ResponseStatus(NO_CONTENT)
  @PreAuthorize("hasRole('CUSTOMER')")
  public void deleteDevice(@PathVariable(value ="id", required = false) Long customerId, @PathVariable("deviceId") Long deviceId) {
    try {
      deviceService.delete(deviceId);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
