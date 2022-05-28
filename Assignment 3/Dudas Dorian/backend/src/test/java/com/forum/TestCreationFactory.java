package com.forum;

import com.forum.category.model.Category;
import com.forum.category.model.dto.CategoryDTO;
import com.forum.post.model.Post;
import com.forum.post.model.dto.PostDTO;
import com.forum.thread.model.Topic;
import com.forum.thread.model.dto.TopicDTO;
import com.forum.user.dto.UserListDTO;
import com.forum.user.model.User;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
        } else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else if (cls.equals(Category.class)) {
            supplier = TestCreationFactory::newCategory;
        } else if (cls.equals(CategoryDTO.class)) {
            supplier = TestCreationFactory::newCategoryDTO;
        } else if (cls.equals(Topic.class)) {
            supplier = TestCreationFactory::newTopic;
        } else if (cls.equals(TopicDTO.class)) {
            supplier = TestCreationFactory::newTopicDTO;
        } else if (cls.equals(Post.class)) {
            supplier = TestCreationFactory::newPost;
        } else if (cls.equals(PostDTO.class)) {
            supplier = TestCreationFactory::newPostDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    public static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .password(randomString())
                .roles(Set.of(randomString()))
                .build();
    }

    public static User newUser() {
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .roles(Set.of())
                .build();
    }

    public static Category newCategory() {
        return Category.builder()
                .id(randomLong())
                .name(randomString())
                .description(randomString())
                .build();
    }

    public static CategoryDTO newCategoryDTO() {
        return CategoryDTO.builder()
                .id(randomLong())
                .name(randomString())
                .description(randomString())
                .build();
    }

    public static Topic newTopic() {
        return Topic.builder()
                .id(randomLong())
                .category(newCategory())
                .user(newUser())
                .title(randomString())
                .content(randomString())
                .creationDate(randomDate())
                .build();
    }

    public static TopicDTO newTopicDTO() {
        return TopicDTO.builder()
                .id(randomLong())
                .categoryName(randomString())
                .originalPoster(randomString())
                .title(randomString())
                .content(randomString())
                .creationDate(randomDate())
                .build();
    }

    public static Post newPost() {
        return Post.builder()
                .id(randomLong())
                .topic(newTopic())
                .user(newUser())
                .content(randomString())
                .creationDate(randomDate())
                .build();
    }

    public static PostDTO newPostDTO() {
        return PostDTO.builder()
                .id(randomLong())
                .topicTitle(randomString())
                .posterUsername(randomString())
                .content(randomString())
                .creationDate(randomDate())
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

    public static Date randomDate() {
        return new Date(randomLong());
    }
}
