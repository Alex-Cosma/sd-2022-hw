package com.rdaniel.sd.a2.backend.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

  @NotBlank(message = "Title is required")
  @Size(min = 3, max = 512, message = "Title must be between 1 and 512 characters")
  private String title;

  @NotBlank(message = "Author is required")
  @Size(min = 1, max = 256, message = "Author must be between 1 and 256 characters")
  private String author;

  @NotBlank(message = "Genre is required")
  @Size(min = 1, max = 128, message = "Genre must be between 1 and 128 characters")
  private String genre;

  private int quantity;

  private double price;

}
