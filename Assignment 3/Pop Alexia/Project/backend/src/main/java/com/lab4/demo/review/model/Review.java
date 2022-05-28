package com.lab4.demo.review.model;

import com.lab4.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Text cannot be null")
    private String text;

    @NotNull(message = "Rating cannot be null")
    private String rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
