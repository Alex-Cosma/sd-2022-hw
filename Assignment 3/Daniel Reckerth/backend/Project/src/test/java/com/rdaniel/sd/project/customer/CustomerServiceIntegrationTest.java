package com.rdaniel.sd.project.customer;

import com.rdaniel.sd.project.customer.dto.CustomerDto;
import com.rdaniel.sd.project.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

import static com.rdaniel.sd.project.TestCreationFactory.newCustomer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerServiceIntegrationTest {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private CustomerRepository customerRepository;

  @Test
  void findById() {
    final Customer customer = newCustomer();
    final Customer saved = customerRepository.save(customer);

    final CustomerDto customerDto = customerService.findById(saved.getId());
    assertEquals(saved.getId(), customerDto.getId());
    assertEquals(customer.getUsername(), customerDto.getUsername());
    assertEquals(customer.getEmail(), customerDto.getEmail());
    assertEquals(customer.getPhoneNumber(), customerDto.getPhoneNumber());
    assertEquals(customer.getAddress(), customerDto.getAddress());
    assertEquals(customer.getBirthDate().toString(), customerDto.getBirthDate());
    assertEquals(customer.getFullName(), customerDto.getFullName());
  }

  @Test
  void update() {
    final Customer customer = newCustomer();
    final Customer saved = customerRepository.save(customer);

    final CustomerDto updatingCustomerDto = CustomerDto.builder()
        .username(customer.getUsername())
        .email(customer.getEmail())
        .phoneNumber(customer.getPhoneNumber())
        .address(customer.getAddress())
        .birthDate(customer.getBirthDate().toString())
        .fullName("Updated Full Name")
        .build();

    final CustomerDto customerDto = customerService.update(saved.getId(), updatingCustomerDto);

    assertEquals(saved.getId(), customerDto.getId());
    assertEquals(customer.getUsername(), customerDto.getUsername());
    assertEquals(customer.getEmail(), customerDto.getEmail());
    assertEquals(customer.getPhoneNumber(), customerDto.getPhoneNumber());
    assertEquals(customer.getAddress(), customerDto.getAddress());
    assertEquals(customer.getBirthDate().toString(), customerDto.getBirthDate());
    assertEquals(updatingCustomerDto.getFullName(), customerDto.getFullName());
  }

  @Test
  void delete() {
    final Customer customer = newCustomer();
    final Customer saved = customerRepository.save(customer);

    customerService.delete(saved.getId());

    final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
        customerService.findById(saved.getId()));

    assertEquals("Customer not found", exception.getMessage());
  }
}