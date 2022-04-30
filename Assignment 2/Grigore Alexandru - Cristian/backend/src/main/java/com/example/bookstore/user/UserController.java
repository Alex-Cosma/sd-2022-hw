package com.example.bookstore.user;


import com.example.bookstore.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bookstore.UrlMappings.USER;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers(){
        return userService.allUsersForList();
    }

    @PutMapping("/{id}")
    public UserListDTO edit(@PathVariable Long id, @RequestBody UserListDTO userListDTO){
        return userService.edit(id,userListDTO);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
