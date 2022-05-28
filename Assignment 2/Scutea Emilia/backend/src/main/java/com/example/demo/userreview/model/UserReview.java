package com.example.demo.userreview.model;

import com.example.demo.bookreview.model.BookReview;
import com.example.demo.bookreview.model.Rating;
import com.example.demo.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReview {
    @Id
    @GeneratedValue
    private Long id;
    private String text;

    @Column(length = 512, nullable = false)
    private String rating;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final UserReview other = (UserReview) obj;
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return this.rating.equals(other.rating);
    }
}
