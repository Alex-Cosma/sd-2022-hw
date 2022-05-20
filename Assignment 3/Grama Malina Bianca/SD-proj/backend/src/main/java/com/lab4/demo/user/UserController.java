package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserCreateDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.USERS;

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
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserCreateDTO user) {
        return userService.create(user);
    }

    @GetMapping(ENTITY)
    public UserListDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @CrossOrigin
    @PutMapping(ENTITY)
    public UserListDTO edit(@PathVariable Long id, @RequestBody UserListDTO user) {
        return userService.edit(id, user);
    }

}
