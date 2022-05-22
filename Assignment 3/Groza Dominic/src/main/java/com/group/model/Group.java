package com.group.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "group_fb")
public class Group {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512,nullable = false,name="group_name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER,
            mappedBy = "groups")
    private Set<User> users = new HashSet<>();



}
