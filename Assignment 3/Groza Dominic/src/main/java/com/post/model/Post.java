package com.post.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@ToString
@Table(name = "post")
public class Post {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="user_id", nullable=false)
    @JsonBackReference
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
