package com.project.clinic.user.model;

import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.treatment.model.Treatment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
        }
)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 120)
    private String password;

    @ManyToMany
    private Set<Treatment> treatments;

    @Column
    private int points;

    @ManyToOne(cascade=CascadeType.DETACH)
    private SkinColor skinColor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();



    public Set<String> getRolesAsString() {
        Set<String> rolesStr = new HashSet<>();
        for(Role r : roles){
            rolesStr.add(r.toString());
        }
        return rolesStr;
    }
}

