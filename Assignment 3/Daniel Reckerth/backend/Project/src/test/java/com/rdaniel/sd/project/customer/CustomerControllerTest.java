package com.rdaniel.sd.project.customer;

import com.rdaniel.sd.project.BaseControllerTest;
import com.rdaniel.sd.project.customer.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;

import static com.rdaniel.sd.project.TestCreationFactory.newCustomerDto;
import static com.rdaniel.sd.project.UrlMappings.CUSTOMER_PATH;
import static com.rdaniel.sd.project.UrlMappings.RESOURCE_BY_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends BaseControllerTest {

  @InjectMocks
  private CustomerController customerController;

  @Mock
  private CustomerService customerService;

  @BeforeEach
  protected void setUp() {
    super.setUp();
    customerController = new CustomerController(customerService);
    mockMvc = MockMvcBuilders.standaloneSetup(customerController)
        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        .build();
  }

  @Test
  void getCustomer_validIdentifier_status200() throws Exception {
    final CustomerDto customerDto = newCustomerDto();
    when(customerService.findById(any(Long.class))).thenReturn(customerDto);

    ResultActions response = performGetWithPathVariable(CUSTOMER_PATH + RESOURCE_BY_ID, 1L);
    response.andExpect(status().isOk())
        .andExpect(jsonContentToBe(customerDto));

    verify(customerService, times(1)).findById(any(Long.class));
    verifyNoMoreInteractions(customerService);
  }

  @Test
  void getCustomer_invalidIdentifier_status404() throws Exception {
    when(customerService.findById(any(Long.class))).thenThrow(EntityNotFoundException.class);

    ResultActions response = performGetWithPathVariable(CUSTOMER_PATH + RESOURCE_BY_ID, 1L);
    response.andExpect(status().isNotFound());

    verify(customerService, times(1)).findById(any(Long.class));
  }

  @Test
  void updateCustomer_validIdentifierAndContent_status200() throws Exception {
    final CustomerDto customerDto = newCustomerDto();
    when(customerService.update(any(Long.class), any(CustomerDto.class))).thenReturn(customerDto);

    final ResultActions resultActions = performPutWithRequestBodyAndPathVariable(CUSTOMER_PATH + RESOURCE_BY_ID, customerDto, 1L);
    resultActions.andExpect(status().isOk())
        .andExpect(jsonContentToBe(customerDto));

    verify(customerService, times(1)).update(any(Long.class), any(CustomerDto.class));
  }

  @Test
  void updateCustomer_invalidIdentifier_status404() throws Exception {
    final CustomerDto customerDto = newCustomerDto();
    when(customerService.update(any(Long.class), any(CustomerDto.class))).thenThrow(EntityNotFoundException.class);

    final ResultActions resultActions = performPutWithRequestBodyAndPathVariable(CUSTOMER_PATH + RESOURCE_BY_ID, customerDto, 1L);
    resultActions.andExpect(status().isNotFound());

    verify(customerService, times(1)).update(any(Long.class), any(CustomerDto.class));
  }

  @Test
  void deleteCustomer_validIdentifier_status204() throws Exception {
    final ResultActions resultActions = performDeleteWithPathVariable(CUSTOMER_PATH + RESOURCE_BY_ID, 1L);
    resultActions.andExpect(status().isNoContent());

    verify(customerService, times(1)).delete(any(Long.class));
  }

  @Test
  void deleteCustomer_invalidIdentifier_status404() throws Exception {
    doThrow(EntityNotFoundException.class).when(customerService).delete(any(Long.class));

    final ResultActions resultActions = performDeleteWithPathVariable(CUSTOMER_PATH + RESOURCE_BY_ID, 1L);
    resultActions.andExpect(status().isNotFound());

    verify(customerService, times(1)).delete(any(Long.class));
  }
}