package com.project.song.model;

import com.project.album.model.Album;
import com.project.playlist.model.Playlist;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "song_playlist",
                joinColumns = @JoinColumn(name = "song_id"),
                inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    @Builder.Default
    private Set<Playlist> playlists = new HashSet<>();



}
