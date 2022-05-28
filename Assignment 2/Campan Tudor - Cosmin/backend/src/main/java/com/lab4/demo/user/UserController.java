package com.lab4.demo.user;

import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public boolean create(@RequestBody UserDTO item) {
         return userService.newUser(item);
    }

    @DeleteMapping(USER_ID_PART)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping(USER_ID_PART)
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO user) {
        return userService.edit(id, user);
    }
}
