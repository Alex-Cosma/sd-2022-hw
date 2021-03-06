package repository.user;

import controller.Response;
import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    Response<Boolean> existsByUsername(String email);

    boolean delete(String username);

    boolean update(Long id, String username,String password);

    Optional<User> findById(Long id);
}