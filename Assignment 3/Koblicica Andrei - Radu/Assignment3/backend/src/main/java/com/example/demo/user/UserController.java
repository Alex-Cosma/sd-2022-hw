package com.example.demo.user;



import com.example.demo.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.demo.UrlMapping.*;


@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin
    @GetMapping
    public List<UserDTO> allUsers() {
        return userService.getAll();
    }

    @CrossOrigin
    @DeleteMapping(USERS_ID_PART)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @CrossOrigin
    @PutMapping(USERS_ID_PART)
    public UserDTO update(@PathVariable Long id, @Valid @RequestBody UserDTO user) {
        return userService.update(id, user);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO user) {
        return userService.create(user);
    }




}
