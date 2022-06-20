package com.rdaniel.sd.project.customer.model;

import com.rdaniel.sd.project.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Customer extends User {

  @Column(length = 216)
  private String fullName;

  @Column(length = 216)
  private String address;

  @Column(length = 20)
  private String phoneNumber;

  @Past
  private LocalDate birthDate;
}
