package com.user.model;


import com.fasterxml.jackson.annotation.*;
import com.group.model.Group;
import com.post.model.Post;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String username;

    @Email
    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 120)
    private String password;

    @Column(name = "first_name" ,nullable = false, length = 120)
    private String firstName;

    @Column(name = "last_name",nullable = false, length = 120)
    private String lastName;

    @Column(nullable = false,length = 200)
    private String address;


    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,orphanRemoval=true)
    private Set<Post> posts;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends=new HashSet<>();


    @JsonBackReference
    @ManyToMany(mappedBy="friends",fetch = FetchType.EAGER)
    private Set<User> friendsOf;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    @JsonIgnore
    private Set<Group> groups = new HashSet<>();

}