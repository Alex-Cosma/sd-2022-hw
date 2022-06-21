package com.rdaniel.sd.project.customer;

import com.rdaniel.sd.project.customer.dto.CustomerDto;
import com.rdaniel.sd.project.customer.mapper.CustomerMapper;
import com.rdaniel.sd.project.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.rdaniel.sd.project.TestCreationFactory.newCustomer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @InjectMocks
  private CustomerService customerService;

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private CustomerMapper customerMapper;

  @Test
  void findById() {
    final Customer customer = newCustomer();
    final CustomerDto customerDto = CustomerMapper.INSTANCE.toDto(customer);

    when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
    when(customerMapper.toDto(customer)).thenReturn(customerDto);

    final CustomerDto result = customerService.findById(customer.getId());

    assertEquals(customer.getId(), result.getId());
    assertEquals(customer.getUsername(), result.getUsername());
    assertEquals(customer.getEmail(), result.getEmail());
    assertEquals(customer.getPhoneNumber(), result.getPhoneNumber());
    assertEquals(customer.getAddress(), result.getAddress());
    assertEquals(customer.getBirthDate().toString(), result.getBirthDate());

    verify(customerRepository, times(1)).findById(customer.getId());
    verify(customerMapper, times(1)).toDto(customer);
    verifyNoMoreInteractions(customerRepository, customerMapper);
  }

  @Test
  void update() {
    final Customer customer = newCustomer();
    final CustomerDto updatingCustomerDto = CustomerMapper.INSTANCE.toDto(customer);
    updatingCustomerDto.setFullName("Updated Full Name");

    when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

    customer.setFullName("Updated Full Name");
    when(customerRepository.save(customer)).thenReturn(customer);
    when(customerMapper.toDto(customer)).thenReturn(updatingCustomerDto);

    final CustomerDto result = customerService.update(customer.getId(), updatingCustomerDto);

    assertEquals(customer.getId(), result.getId());
    assertEquals(customer.getUsername(), result.getUsername());
    assertEquals(customer.getEmail(), result.getEmail());
    assertEquals(customer.getPhoneNumber(), result.getPhoneNumber());
    assertEquals(customer.getAddress(), result.getAddress());
    assertEquals(customer.getBirthDate().toString(), result.getBirthDate());
    assertEquals(updatingCustomerDto.getUsername(), result.getUsername());

    verify(customerRepository, times(1)).findById(customer.getId());
    verify(customerRepository, times(1)).save(customer);
    verify(customerMapper, times(1)).toDto(customer);
    verifyNoMoreInteractions(customerRepository, customerMapper);
  }

  @Test
  void delete() {
    final Customer customer = newCustomer();
    when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));

    customerService.delete(customer.getId());

    verify(customerRepository, times(1)).findById(customer.getId());
    verify(customerRepository, times(1)).deleteById(customer.getId());
    verifyNoMoreInteractions(customerRepository);
  }
}