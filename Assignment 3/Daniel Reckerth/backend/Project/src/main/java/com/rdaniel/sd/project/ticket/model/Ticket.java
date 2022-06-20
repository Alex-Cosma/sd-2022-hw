package com.rdaniel.sd.project.ticket.model;

import com.rdaniel.sd.project.device.model.Device;
import com.rdaniel.sd.project.ticket.model.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.OnDeleteAction.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(length = 512, nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  private StatusType status;

  private LocalDateTime createdAt = LocalDateTime.now();

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "fk_device")
  @OnDelete(action = CASCADE)
  private Device device;
}
