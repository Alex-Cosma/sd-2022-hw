package com.rdaniel.sd.project.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto {

  private Long id;
  private String description;
  private String status;
  private String createdAt;
}
