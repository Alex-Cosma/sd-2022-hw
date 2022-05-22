package com.post.model;

import com.fasterxml.jackson.annotation.*;
import com.user.dto.UserListDto;
import com.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString
@Table(name = "post")
public class Post {

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_body",nullable = false)
    private String body;

    @Column(nullable = false)
    private Long likes;

    @Column(nullable = false)
    private Long disLikes;

    @Column(nullable = false)
    private Date created_at;


}
