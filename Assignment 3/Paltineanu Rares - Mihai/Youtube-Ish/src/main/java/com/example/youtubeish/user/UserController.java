package com.example.youtubeish.user;

import com.example.youtubeish.security.dto.MessageResponse;
import com.example.youtubeish.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.youtubeish.UrlMapping.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(GET_USERS)
    public List<UserDTO> allUsers() {
        return userService.allUsersDto();
    }

    @DeleteMapping(DELETE_USER)
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }

    @PostMapping(ADD_USER)
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        userService.create(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping(GET_USER)
    public UserDTO findUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping(UPDATE_USER)
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO user) {
        return userService.edit(id, user);
    }
}
