package com.rdaniel.sd.project.customer;

import com.rdaniel.sd.project.customer.dto.CustomerDto;
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

import static com.rdaniel.sd.project.UrlMappings.CUSTOMER_PATH;
import static com.rdaniel.sd.project.UrlMappings.RESOURCE_BY_ID;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(CUSTOMER_PATH)
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Api("Set of endpoints for customer CRUD operations")
public class CustomerController {

  private final CustomerService customerService;

  @ApiOperation(httpMethod = "GET", value = "Returns all customers")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Customers found"),
      @ApiResponse(code = 404, message = "Customer not found")
  })
  @GetMapping
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
  public List<CustomerDto> getCustomers() {
    try {
      return customerService.findAll();
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Returns customer by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Customer found", response = CustomerDto.class),
      @ApiResponse(code = 404, message = "Customer not found")
  })
  @GetMapping(RESOURCE_BY_ID)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('CUSTOMER')")
  public CustomerDto getCustomer(@PathVariable Long id) {
    try {
      return customerService.findById(id);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "PUT", value = "Update customer by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Customer updated", response = CustomerDto.class),
      @ApiResponse(code = 404, message = "Customer not found")
  })
  @PutMapping(RESOURCE_BY_ID)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('CUSTOMER')")
  public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
    try {
      return customerService.update(id, customerDto);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @ApiOperation(httpMethod = "DELETE", value = "Delete customer by id")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Customer deleted"),
      @ApiResponse(code = 404, message = "Customer not found")
  })
  @DeleteMapping(RESOURCE_BY_ID)
  @ResponseStatus(NO_CONTENT)
  @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
  public void deleteCustomer(@PathVariable Long id) {
    try {
      customerService.delete(id);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
