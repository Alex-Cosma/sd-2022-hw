package com.rdaniel.sd.project.ticket;

import com.rdaniel.sd.project.device.dto.DeviceDto;
import com.rdaniel.sd.project.ticket.dto.TicketDto;
import com.rdaniel.sd.project.ticket.model.enums.StatusType;
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

import static com.rdaniel.sd.project.UrlMappings.TICKETS_PATH;
import static com.rdaniel.sd.project.UrlMappings.TICKET_ID_PATH;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(TICKETS_PATH)
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Api("Set of endpoints for managing device's tickets")
public class TicketController {

  private final TicketService ticketService;

  @ApiOperation(httpMethod = "GET", value = "Get device's tickets")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Tickets retrieved", response = TicketDto.class),
      @ApiResponse(code = 404, message = "Device not found")
  })
  @GetMapping
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
  public List<TicketDto> findAllDeviceTickets(@PathVariable(value = "id", required = false) Long customerId,
                                              @PathVariable(value = "deviceId") Long deviceId) {
    try {
      return ticketService.findAllByDeviceId(deviceId);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Get ticket by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ticket returned", response = TicketDto.class),
      @ApiResponse(code = 404, message = "Device not found")
  })
  @GetMapping(TICKET_ID_PATH)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
  public TicketDto findTicket(@PathVariable(value = "id", required = false) Long customerId,
                              @PathVariable(value = "deviceId", required = false) Long deviceId,
                              @PathVariable("ticketId") Long ticketId) {
    try {
      return ticketService.findById(ticketId);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "POST", value = "Create ticket for device")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Ticket created", response = TicketDto.class),
      @ApiResponse(code = 404, message = "Device not found")
  })
  @PostMapping
  @ResponseStatus(CREATED)
  @PreAuthorize("hasRole('CUSTOMER')")
  public TicketDto createTicket(@PathVariable(value = "id", required = false) Long customerId,
                                @PathVariable(value = "deviceId", required = false) Long deviceId,
                                @RequestBody TicketDto ticketDto) {
    try {
      return ticketService.create(deviceId, ticketDto);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "PUT", value = "Update ticket by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ticket updated", response = TicketDto.class),
      @ApiResponse(code = 404, message = "Device not found")
  })
  @PutMapping(TICKET_ID_PATH)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('CUSTOMER')")
  public TicketDto updateTicket(@PathVariable(value = "id", required = false) Long customerId,
                                @PathVariable(value = "deviceId", required = false) Long deviceId,
                                @PathVariable("ticketId") Long ticketId,
                                @RequestBody TicketDto ticketDto) {
    try {
      return ticketService.update(ticketId, ticketDto);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "PATCH", value = "Change ticket status")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ticket status changed", response = TicketDto.class),
      @ApiResponse(code = 404, message = "Device not found")
  })
  @PatchMapping(TICKET_ID_PATH)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('EMPLOYEE')")
  public TicketDto changeTicketStatus(@PathVariable(value = "id", required = false) Long customerId,
                              @PathVariable(value = "deviceId", required = false) Long deviceId,
                              @PathVariable("ticketId") Long ticketId,
                                      @RequestBody StatusType statusType) {
    try {
      return ticketService.changeStatus(ticketId, statusType);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "DELETE", value = "Delete ticket by id")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Ticket deleted"),
      @ApiResponse(code = 404, message = "Device not found")
  })
  @DeleteMapping(TICKET_ID_PATH)
  @ResponseStatus(NO_CONTENT)
  @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
  public void deleteTicket(@PathVariable(value = "id", required = false) Long customerId,
                           @PathVariable(value = "deviceId", required = false) Long deviceId,
                            @PathVariable("ticketId") Long ticketId) {
    try {
      ticketService.delete(ticketId);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
