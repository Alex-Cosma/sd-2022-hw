package com.rdaniel.sd.project.user;

import com.rdaniel.sd.project.ticket.dto.TicketDto;
import com.rdaniel.sd.project.user.dto.UserDto;
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
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(USERS_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Api("Set of endpoints for managing users")
public class UserController {
  private final UserService userService;

  @ApiOperation(httpMethod = "GET", value = "Get all users")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "All users retrieved", response = UserDto.class),
  })
  @GetMapping
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserDto> getAllUsers() {
    return userService.findAllUsers();
  }

  @ApiOperation(httpMethod = "GET", value = "Get all employees")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "All employees users retrieved", response = UserDto.class),
  })
  @GetMapping(REGULAR_USERS_PATH)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserDto> getAllEmployees() {
    try {
      return userService.findAllEmployees();
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Get employee by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Employee returned", response = UserDto.class),
      @ApiResponse(code = 404, message = "Employee not found")
  })
  @GetMapping(RESOURCE_BY_ID)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('ADMIN')")
  public UserDto getEmployee(@PathVariable Long id) {
    try {
      return userService.findEmployeeById(id);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "PUT", value = "Update employee by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Updated employee returned", response = UserDto.class),
      @ApiResponse(code = 404, message = "Employee not found")
  })
  @PutMapping(RESOURCE_BY_ID)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('ADMIN')")
  public UserDto updateEmployee(@PathVariable Long id, @RequestBody UserDto regularUserDto) {
    try {
      return userService.updateEmployee(id, regularUserDto);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "DELETE", value = "Delete employee by id")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Employee deleted"),
      @ApiResponse(code = 404, message = "Employee not found")
  })
  @DeleteMapping(RESOURCE_BY_ID)
  @ResponseStatus(NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteEmployee(@PathVariable Long id) {
    try {
      userService.deleteEmployee(id);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
