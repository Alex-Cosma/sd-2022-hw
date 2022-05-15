package com.example.youtubeish.video.model;

import com.example.youtubeish.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
