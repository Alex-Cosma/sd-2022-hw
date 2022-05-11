package com.example.youtubeish.video.model;

import com.example.youtubeish.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length=128, nullable = false)
    private String title;

    @Column(length=256, nullable = false)
    private String thumbnailUrl;

    @Column(length=1024)
    private String description;

    @Column(length=128, nullable = false)
    private String channelTitle;

    @Column(length=11, nullable = false, unique = true)
    private String videoUrl;
}
