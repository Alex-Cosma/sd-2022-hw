package com.rdaniel.sd.project.device.model;

import com.rdaniel.sd.project.customer.model.Customer;
import com.rdaniel.sd.project.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;


import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(length = 256, nullable = false)
  private String name;

  @Column(length = 256, nullable = false)
  private String brand;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "fk_user")
  @OnDelete(action = CASCADE)
  private Customer customer;
}
