package com.lab4.demo.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long id;
    @NotNull(message = "Text cannot be null")
    private String text;
    private String reviewer;
    @NotNull(message = "Rating cannot be null")
    private String rating;

}
