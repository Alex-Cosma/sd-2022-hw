package com.project.album.model;

import com.project.record.model.Record;
import com.project.song.model.Song;
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
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "album")
    @Builder.Default
    private Set<Song> songs = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;
}
