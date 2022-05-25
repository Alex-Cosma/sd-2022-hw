package com.project.song;

import com.project.TestCreationFactory;
import com.project.song.model.Song;
import com.project.user.RoleRepository;
import com.project.user.UserRepository;
import com.project.user.model.ERole;
import com.project.user.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SongRepositoryTest {

    @Autowired
    private SongRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Song songSaved = repository.save(Song.builder().title("whatever").artist("Ayho").build());

        assertNotNull(songSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Song.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Song> songs = TestCreationFactory.listOf(Song.class);
        repository.saveAll(songs);
        List<Song> all = repository.findAll();
        assertEquals(songs.size(), all.size());
    }


    private void buildRoles() {
        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }
    }

}
