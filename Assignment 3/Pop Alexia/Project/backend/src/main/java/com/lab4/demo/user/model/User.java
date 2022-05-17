package com.lab4.demo.user.model;

import com.lab4.demo.quizzSession.model.QuizzSession;
import com.lab4.demo.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, message = "Username must be at least 4 characters long")
    @NotNull
    @Column(nullable = false, length = 20)
    private String username;

    @Email(message = "Email is not valid")
    @NotNull(message = "Email is required")
    @Column(nullable = false, length = 50)
    private String email;

    @Size(min = 6 , message = "Password must be at least 6 characters long")
    @Value("noedit")
    @Column(nullable = false, length = 120)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Collection<QuizzSession> quizzSessions;

    @OneToMany(mappedBy = "user",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private Collection<Review> reviews;

    @Column
    private Integer rankingPoints;
}
