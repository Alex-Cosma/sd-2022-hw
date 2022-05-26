package com.forum.thread.model;


import com.forum.category.model.Category;
import com.forum.user.model.User;
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
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Category category;

    @ManyToOne
    @JoinColumn
    private User user;

    @Column(nullable = false, length = 128)
    private String title;

    @Column(length = 2048)
    private String content;

    @Column(nullable = false)
    private Date creationDate;
}
