package com.policeSection.user;

import com.policeSection.user.dto.UserDTO;
import com.policeSection.user.dto.UserListDTO;
import com.policeSection.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.policeSection.UrlMapping.USER_ID_PART;

@RestController
@RequestMapping(UrlMapping.USER)
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
