package com.example.youtubeish.video.model;

import com.example.youtubeish.comment.model.Comment;
import com.example.youtubeish.playlist.model.Playlist;
import com.example.youtubeish.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(
        name = "\"video\"")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=128, nullable = false)
    private String title;

    @Column(length=256, nullable = false)
    private String thumbnailUrl;

    @Column(length=1024)
    private String description;

    @Column(length=128, nullable = false)
    private String channelTitle;

    @Column(length=11, nullable = false, unique = true)
    private String videoId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "video", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
