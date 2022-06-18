package com.app.bookingapp.controller;

import com.app.bookingapp.data.dto.model.UserDto;
import com.app.bookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.bookingapp.utils.URLMapping.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> allUsers(){
        return userService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDto create(@RequestBody UserDto user) {
        return userService.create(user);
    }

    @PatchMapping(ID)
    public UserDto update(@PathVariable Long id, @RequestBody UserDto user) {
        return userService.update(id, user);
    }

    @DeleteMapping(USERNAME)
    public void delete(@PathVariable String username) {
        userService.delete(username);
    }
}
