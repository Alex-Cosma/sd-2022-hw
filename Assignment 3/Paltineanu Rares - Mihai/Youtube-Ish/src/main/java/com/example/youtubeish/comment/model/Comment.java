package com.example.youtubeish.comment.model;

import com.example.youtubeish.video.model.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import com.example.youtubeish.user.model.User;
import javax.persistence.*;

@Entity
@Table(
        name = "\"comment\""
)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 512)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;
}
