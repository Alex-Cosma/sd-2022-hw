package com.app.user;

import com.app.UrlMapping;
import com.app.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.UrlMapping.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> allUsers() {
        return userService.allUsers();
    }

    @PostMapping(USER_CREATE)
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PatchMapping(USER_EDIT)
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.edit(id, userDTO);
    }

}
