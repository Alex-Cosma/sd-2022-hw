package com.example.bookstore.book.model.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
@Getter
@Setter
public class BookDTO {
    private Long id;

    @NotNull
    @Pattern(regexp="^[A-Za-z .]*$", message = "Title must contain only letters")
    private String title;

    @NotNull
    @Pattern(regexp="^[A-Za-z .]*$",message = "Author must contain only letters")
    private String author;

    @NotNull
    @Pattern(regexp="^[A-Za-z]*$",message = "Genre must contain only letters")
    private String genre;

    @Min(value = 0, message = "Quantity must be positive")
    @Pattern(regexp="^[0-9]*$",message = "Quantity must contain only digits")
    @NotNull(message = "Quantity must be a number")
    private int quantity;

    @Min(value = 0, message = "Price must be positive")
    @Pattern(regexp="^[0-9]*$",message = "Price must contain only digits")
    @NotNull(message = "Price must be a number")
    private double price;
}
