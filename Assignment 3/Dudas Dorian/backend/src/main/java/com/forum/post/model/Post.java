package com.forum.post.model;


import com.forum.user.model.User;
import com.forum.thread.model.Topic;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Topic topic;

    @ManyToOne
    @JoinColumn
    private User user;

    @Column(nullable = false, length = 2048)
    private String content;

    @Column(nullable = false)
    private Date creationDate;
}
