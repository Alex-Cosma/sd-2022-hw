package com.example.bookstore.user;


import com.example.bookstore.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.bookstore.UrlMapping.*;


@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin
    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @CrossOrigin
    @DeleteMapping(USERS_ID_PART)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @CrossOrigin
    @PutMapping(USERS_ID_PART)
    public UserListDTO update(@PathVariable Long id, @Valid @RequestBody UserListDTO user) {
        return userService.update(id, user);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserListDTO user) {
        return userService.create(user);
    }



}
