package com.example.bookstore.user;

import com.example.bookstore.security.dto.MessageResponse;
import com.example.bookstore.user.model.dto.UserDTO;
import com.example.bookstore.user.model.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.bookstore.UrlMapping.USER;
import static com.example.bookstore.UrlMapping.USER_ID_PATH;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserDTO user) {

        userService.create(user);

        return ResponseEntity.ok(new MessageResponse("User created successfully"));
    }

    @CrossOrigin(origins = "*")
    @PatchMapping(USER_ID_PATH)
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody @Valid UserDTO user) {

        userService.edit(id, user);

        return ResponseEntity.ok(new MessageResponse("User edited successfully"));
    }

    @DeleteMapping(USER_ID_PATH)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    }
}
