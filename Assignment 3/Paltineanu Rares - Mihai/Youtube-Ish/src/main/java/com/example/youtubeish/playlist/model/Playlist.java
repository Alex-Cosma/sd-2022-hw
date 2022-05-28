package com.example.youtubeish.playlist.model;

import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.model.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "\"playlist\""
)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "playlist_videos",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id"))
    @Builder.Default
    private List<Video> videos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void removeVideo(Video video) {
        this.videos.remove(video);
    }
}
