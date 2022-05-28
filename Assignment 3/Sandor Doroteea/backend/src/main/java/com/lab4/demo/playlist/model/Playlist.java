package com.lab4.demo.playlist.model;

import com.lab4.demo.track.model.Track;
import com.lab4.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
                @UniqueConstraint(columnNames = "title")
        })
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "playlist_tracks",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
    @Builder.Default
    private List<Track> tracks = new ArrayList<>();

    @Column(length = 15, nullable = false)
    private Integer duration;

    public void setDuration(){
        this.duration = 0;
        for(Track track: this.tracks) {
            this.duration += track.getDuration();
        }
    }
}
