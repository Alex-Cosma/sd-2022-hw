package com;

import com.group.model.Group;
import com.group.model.dto.GroupDto;
import com.post.model.Post;
import com.post.model.dto.PostDto;
import com.user.dto.UserListDto;

import java.time.Instant;
import java.util.Date;
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

        if (cls.equals(UserListDto.class)) {
            supplier = TestCreationFactory::newUserListDto;
        } else if (cls.equals(Post.class)) {
            supplier = TestCreationFactory::newPost;
        } else if (cls.equals(PostDto.class)) {
            supplier = TestCreationFactory::newPostDto;
        } else if (cls.equals(Group.class)) {
            supplier = TestCreationFactory::newGroup;
        } else if (cls.equals(GroupDto.class)) {
            supplier = TestCreationFactory::newGroupDto;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static UserListDto newUserListDto() {
        return UserListDto.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .firstName(randomString())
                .lastName(randomString())
                .address(randomString())
                .build();
    }

    private static Post newPost() {
        return Post.builder()
                .id(randomLong())
                .body(randomString())
                .created_at(Date.from(Instant.now()))
                .likes(randomLong())
                .disLikes(randomLong())
                .build();

    }

    private static PostDto newPostDto() {
        return PostDto.builder()
                .id(randomLong())
                .body(randomString())
                .created_at(Date.from(Instant.now()))
                .likes(randomLong())
                .disLikes(randomLong())
                .build();
    }
    private static Group newGroup() {
        return Group.builder()
                .name(randomString())
                .id(randomLong())
                .build();
    }
    private static GroupDto newGroupDto() {
        return GroupDto.builder()
                .name(randomString())
                .id(randomLong())
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
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
}
