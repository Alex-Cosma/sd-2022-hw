package services.admin;

import model.User;
import model.validation.Notification;
import repositories.user.UserRepository;
import services.user.AuthenticationService;

import java.util.List;

public class AdminServiceImplementation implements AdminService{

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    public AdminServiceImplementation(AuthenticationService authenticationService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @Override
    public Notification<Boolean> createEmployee(String employeeUsername, String employeePassword) {
        return authenticationService.register(employeeUsername,employeePassword);
    }

    @Override
    public Notification<Boolean> deleteEmployee(String username) {
        return userRepository.delete(username);
    }

    @Override
    public List<User> viewAllEmployees() {
        return userRepository.findAll();
    }


}
