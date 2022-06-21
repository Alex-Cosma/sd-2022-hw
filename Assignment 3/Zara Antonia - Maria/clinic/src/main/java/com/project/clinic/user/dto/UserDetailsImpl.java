package com.project.clinic.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.user.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String username;

    private Set<Treatment> treatments;

    private int points;

    private String skinColor;


    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String password, int points,
                           Collection<? extends GrantedAuthority> authorities,
                           Set<Treatment> treatments, String skinColor) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.points = points;
        this.treatments = treatments;
        this.skinColor = skinColor;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getPoints(),
                authorities,
                user.getTreatments(),
                user.getSkinColor().toString());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public String toString(){
        return username + " " + password;
    }
}