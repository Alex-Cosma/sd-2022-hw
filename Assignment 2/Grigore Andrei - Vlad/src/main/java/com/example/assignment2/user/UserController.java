package com.example.assignment2.user;

import com.example.assignment2.user.dto.UserListDTO;
import com.example.assignment2.user.dto.UserMinimalDTO;
import com.example.assignment2.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.assignment2.UrlMappings.ENTITY;
import static com.example.assignment2.UrlMappings.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers(){
        return userService.allUsersForList();
    }

    @PatchMapping("/edit/{id}")
    public UserListDTO edit(@PathVariable Long id, @RequestBody UserListDTO user){return userService.edit(id,user);}

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){userService.delete(id);}

    @PostMapping("/create")
    public User create(@RequestBody User user){return userService.create(user);}


}
