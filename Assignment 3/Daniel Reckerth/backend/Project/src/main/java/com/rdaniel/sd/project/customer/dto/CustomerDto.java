package com.rdaniel.sd.project.customer.dto;


import com.rdaniel.sd.project.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerDto extends UserDto {

  private String fullName;

  private String address;

  private String phoneNumber;

  private String birthDate;
}
