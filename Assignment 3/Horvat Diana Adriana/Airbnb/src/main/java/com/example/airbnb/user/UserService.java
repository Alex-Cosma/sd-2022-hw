package com.example.airbnb.user;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.booking.BookingService;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.security.AuthService;
import com.example.airbnb.security.dto.MessageResponse;
import com.example.airbnb.security.dto.SignupRequest;
import com.example.airbnb.user.dto.UserListDTO;
import com.example.airbnb.user.dto.UserMinimalDTO;
import com.example.airbnb.user.mapper.UserMapper;
import com.example.airbnb.user.model.*;
import lombok.RequiredArgsConstructor;
import org.sevensource.commons.email.service.EmailSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
//    private final UserMapper userMapper;
//    private final AuthService authService;
//    private final PasswordEncoder encoder;

//    public List<UserMinimalDTO> allUsersMinimal() {
//        return userRepository.findAll()
//                .stream().map(userMapper::userMinimalFromUser)
//                .collect(toList());
//    }
//
//    public List<UserListDTO> allUsersForList() {
//        return userRepository.findAll()
//                .stream().map(user -> {
//                    UserListDTO userListDTO = userMapper.userListDtoFromUser(user);
//                    userMapper.populateRoles(user, userListDTO);
//                    userMapper.populateAccommodations(user, userListDTO);
//                    return userListDTO;
//                }).collect(toList());
//    }
//
//    public ResponseEntity<?> create(UserListDTO user){
//        if (authService.existsByUsername(user.getName())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (authService.existsByEmail(user.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        SignupRequest signupRequest = new SignupRequest(user.getName(), user.getEmail(), user.getPassword(), null);
//        authService.register(signupRequest);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
//
//    public void delete(Long id) {
//        userRepository.deleteById(id);
//    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }
//
//    public UserListDTO edit(Long id, UserListDTO user) {
//        User actUser = findById(id);
//        actUser.setUsername(user.getName());
//        actUser.setEmail(user.getEmail());
//        if(!Objects.equals(user.getPassword(), actUser.getPassword())){
//            actUser.setPassword(encoder.encode(user.getPassword()));
//        }
//
//        return userMapper.userListDtoFromUser(
//                userRepository.save(actUser)
//        );
//    }

    public Set<Accommodation> getUserAccommodations(Long user_id){
        return findById(user_id).getAccommodations();
    }

    public Set<Booking> getUsersBookings(Long user_id){
        return findById(user_id).getBookings();
    }

    public void addUserHostRole(Long user_id){
        User user = findById(user_id);
        Set<Role> roles = user.getRoles();
        List<Role> rolesList = new ArrayList<>(roles);
        Role role = roleRepository.findByName(ERole.HOST)
                .orElseThrow(() -> new RuntimeException("Cannot find HOST role"));

        rolesList.add(role);
        Set<Role> newRoles = new HashSet<>(rolesList);
        user.setRoles(newRoles);
        userRepository.save(user);
    }
}

