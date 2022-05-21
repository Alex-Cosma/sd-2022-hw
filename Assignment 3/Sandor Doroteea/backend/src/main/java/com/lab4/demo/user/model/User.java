package com.lab4.demo.user.model;

import com.lab4.demo.payment.model.Payment;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.track.model.Track;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

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

    @Column(nullable = false, length = 20)
    private String username;

    @Email
    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 120)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    @OnDelete(action = CASCADE)
    private Set<Role> roles = new HashSet<>();

    //considering each user has private playlists
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_playlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    @Builder.Default
    private List<Playlist> playlistList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_tracks",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
    @Builder.Default
    private Set<Track> purchasedTracks = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_payments",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_id"))
    @Builder.Default
    private Set<Payment> payments = new HashSet<>();
}
