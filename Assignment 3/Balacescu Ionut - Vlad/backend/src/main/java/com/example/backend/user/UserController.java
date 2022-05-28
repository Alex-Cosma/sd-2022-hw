package com.example.backend.user;

import com.example.backend.user.dto.UserDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.backend.UrlMapping.USER;
import static com.example.backend.UrlMapping.USER_ID_PART;


@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> allUsers() {

        return userService.allUsersForList();
    }

    @PatchMapping(USER_ID_PART)
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO user) {
        return userService.edit(id, user);
    }
    @DeleteMapping(USER_ID_PART)
    public void delete(@PathVariable Long id){
        userService.delete(id);}

}
