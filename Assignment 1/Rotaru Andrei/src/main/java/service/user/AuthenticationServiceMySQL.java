package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class AuthenticationServiceMySQL implements AuthenticationService{
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password) {
        Role userRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(userRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        List<User> allUsers = userRepository.findAll();
        for(User user1 : allUsers){
            if (user1.getUsername().equals(user.getUsername())) {
                userRegisterNotification.addError("Already existing id!");
                userRegisterNotification.setResult(Boolean.FALSE);
                return userRegisterNotification;
            }
        }

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<Boolean> registerAsAdmin(String username, String password) {
        Role userRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(userRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        List<User> allUsers = userRepository.findAll();
        for(User user1 : allUsers){
            if (user1.getUsername().equals(user.getUsername())) {
                userRegisterNotification.addError("Already existing id!");
                userRegisterNotification.setResult(Boolean.FALSE);
                return userRegisterNotification;
            }
        }

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, encodePassword(password));
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
