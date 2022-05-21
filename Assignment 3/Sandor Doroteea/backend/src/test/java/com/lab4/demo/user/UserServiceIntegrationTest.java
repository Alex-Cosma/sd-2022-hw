package com.lab4.demo.user;

import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.model.dto.TrackDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void findAll() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getName());
        }
    }

    @Test
    void create() {
        UserListDTO userListDTO = UserListDTO.builder()
                .name("User")
                .password("password")
                .email("email@yahoo.com")
                .build();
        UserListDTO userListDTOResponse = userService.createUser(userListDTO);
        assertEquals(userListDTO.getName(), userListDTOResponse.getName());

    }

    @SneakyThrows
    @Test
    void update() {
        UserListDTO user = UserListDTO.builder()
                .name("User")
                .password("password")
                .email("email@yahoo.com")
                .build();
        UserListDTO savedUser = userService.createUser(user);
        savedUser.setName("User2");
        UserListDTO editedUser = userService.updateUser(savedUser.getId(), savedUser);
        Assertions.assertNotEquals(user.getName(), editedUser.getName());
    }

    @Test
    void delete() {
        UserListDTO user = UserListDTO.builder()
                .name("User")
                .password("password")
                .email("email@yahoo.com")
                .build();
        UserListDTO savedUser = userService.createUser(user);
        userService.deleteUser(savedUser.getId());
        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    void buyTrack() throws UserNotFoundException {
        TrackDTO track = TrackDTO.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        UserListDTO user = userService.createUser(
                UserListDTO.builder()
                .id(1L)
                .name("User")
                .password("password")
                .email("email@yahoo.com")
                .purchasedTracks(new ArrayList<>())
                .build());
        UserListDTO userListDTO = userService.buyTrack(user.getId(),track);

        assertEquals(track.getTitle(),userListDTO.getPurchasedTracks().get(0).getTitle());
    }

    @Test
    void createPlaylist() throws UserNotFoundException {
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist 2")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        UserListDTO user = userService.createUser(
                UserListDTO.builder()
                        .id(1L)
                        .name("User")
                        .password("password")
                        .email("email@yahoo.com")
                        .purchasedTracks(new ArrayList<>())
                        .playlistList(new ArrayList<>())
                        .build());

        UserListDTO userListDTO = userService.createPlaylist(user.getId(),playlistDTO);

        assertEquals(playlistDTO.getTitle(),userListDTO.getPlaylistList().get(0).getTitle());
    }
}