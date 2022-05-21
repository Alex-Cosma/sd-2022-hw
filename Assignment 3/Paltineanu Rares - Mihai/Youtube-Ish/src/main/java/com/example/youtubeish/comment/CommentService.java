package com.example.youtubeish.comment;

import com.example.youtubeish.comment.mapper.CommentMapper;
import com.example.youtubeish.comment.model.Comment;
import com.example.youtubeish.comment.dto.CommentDTO;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.mapper.UserMapper;
import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.model.Video;
import com.example.youtubeish.video.dto.VideoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    public List<CommentDTO> getAllCommentsFromVideo(Long id) {
        return commentRepository.getAllByVideoId(id).stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    public Comment create(String content, UserDTO userDTO, VideoDTO videoDTO) {
        User user = userMapper.fromDto(userDTO);
        Video video = Video.builder()
                .id(videoDTO.getId())
                .channelTitle(videoDTO.getChannelTitle())
                .description(videoDTO.getDescription())
                .videoId(videoDTO.getVideoId())
                .title(videoDTO.getTitle())
                .thumbnailUrl(videoDTO.getThumbnailUrl())
                .user(user)
                .build();
        Comment comment = Comment.builder()
                .content(content)
                .user(user)
                .video(video)
                .build();
        return commentRepository.save(comment);
    }

    public List<Comment> getUserComments(Long id) {
        return commentRepository.getAllByUserId(id);
    }
}
