package com.example.youtubeish;

import com.example.youtubeish.comment.dto.AddCommentDTO;
import com.example.youtubeish.comment.dto.CommentDTO;
import com.example.youtubeish.comment.model.Comment;
import com.example.youtubeish.playlist.dto.PlaylistDTO;
import com.example.youtubeish.playlist.model.Playlist;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.dto.UserListDTO;
import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.dto.VideoDTO;
import com.example.youtubeish.video.dto.api.*;
import com.example.youtubeish.video.model.Video;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {
    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                        (T) finalSupplier.get()
                ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static UserDTO newUserDto() {
        return UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
    }

    public static User newUser() {
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
    }

    public static Video newVideo() {
        return Video.builder()
                .description(randomString())
                .thumbnailUrl(randomString())
                .channelTitle(randomString())
                .videoId(randomString())
                .title(randomString())
                .user(newUser())
                .build();
    }

    public static VideoDTO newVideoDTO() {
        return VideoDTO.builder()
                .description(randomString())
                .thumbnailUrl(randomString())
                .channelTitle(randomString())
                .videoId(randomString())
                .title(randomString())
                .user(newUserDto())
                .build();
    }

    public static Comment newComment() {
        return Comment.builder()
                .user(newUser())
                .video(newVideo())
                .content(randomString())
                .id(randomLong())
                .build();
    }

    public static CommentDTO fromComment(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .author(comment.getUser().getUsername())
                .content(comment.getContent())
                .build();
    }

    public static Comment fromAddCommentDTO(AddCommentDTO addCommentDTO) {
        return Comment.builder()
                .content(addCommentDTO.getContent())
                .video(videoFromDTO(addCommentDTO.getVideo()))
                .user(fromDto(addCommentDTO.getUser()))
                .build();
    }

    public static Playlist newPlaylist() {
        return Playlist.builder()
                .id(randomLong())
                .videos(List.of(
                        newVideo(),
                        newVideo(),
                        newVideo())
                )
                .user(newUser())
                .build();
    }

    public static PlaylistDTO toPlaylistDTO(Playlist playlist) {
        return PlaylistDTO.builder()
                .id(playlist.getId())
                .user(toDto(playlist.getUser()))
                .videos(playlist.getVideos().stream().map(TestCreationFactory::videoToDTO).collect(toList()))
                .build();
    }

    public static VideoDTO videoToDTO(Video video) {
        return VideoDTO.builder()
                .id(video.getId())
                .description(video.getDescription())
                .channelTitle(video.getChannelTitle())
                .videoId(video.getVideoId())
                .title(video.getTitle())
                .user(toDto(video.getUser()))
                .build();
    }

    public static Video videoFromDTO(VideoDTO video) {
        return Video.builder()
                .id(video.getId())
                .description(video.getDescription())
                .channelTitle(video.getChannelTitle())
                .videoId(video.getVideoId())
                .title(video.getTitle())
                .thumbnailUrl(video.getThumbnailUrl())
                .user(fromDto(video.getUser()))
                .build();
    }

    public static VideoAPIDTO newVideoAPIDTO() {
        return VideoAPIDTO.builder()
                .id(VideoID.builder()
                        .videoId(randomString())
                        .build())
                .snippet(Snippet.builder()
                        .description(randomString())
                        .title(randomString())
                        .channelTitle(randomString())
                        .thumbnail(Thumbnail.builder()
                                .mediumThumbnail(MediumThumbnail.builder()
                                        .url(randomString())
                                        .build())
                                .build())
                        .build())
                .build();
    }

    public static UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static User fromDto(UserDTO user) {
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }


}
