package com.rdaniel.sd.project.customer;

import com.rdaniel.sd.project.customer.dto.CustomerDto;
import com.rdaniel.sd.project.customer.mapper.CustomerMapper;
import com.rdaniel.sd.project.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  private final CustomerMapper customerMapper;

  public List<CustomerDto> findAll() {
    return customerRepository.findAll().stream()
        .map(customerMapper::toDto)
        .collect(Collectors.toList());
  }

  public CustomerDto findById(Long id) {
    return customerRepository.findById(id)
        .map(customerMapper::toDto)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
  }

  public CustomerDto update(Long id, CustomerDto customerDto) {
    final Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

    customer.setFullName(customerDto.getFullName());
    customer.setAddress(customerDto.getAddress());
    customer.setBirthDate(LocalDate.parse(customerDto.getBirthDate()));
    customer.setPhoneNumber(customerDto.getPhoneNumber());

    return customerMapper.toDto(customerRepository.save(customer));
  }

  public void delete(Long id) {
    final Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

    customerRepository.deleteById(id);
  }
}
