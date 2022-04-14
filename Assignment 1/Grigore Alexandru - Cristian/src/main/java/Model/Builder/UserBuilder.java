package Model.Builder;

import Model.Role;
import Model.User;
import Repository.User.UserRepositoryMySQL;

import java.util.List;

public class UserBuilder {

    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder setId(Long id){
        user.setId(id);
        return this;
    }

    public UserBuilder setUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder setRole(Role role) {
        user.setRoles(role);
        return this;
    }

    public User build() {
        return user;
    }
}
