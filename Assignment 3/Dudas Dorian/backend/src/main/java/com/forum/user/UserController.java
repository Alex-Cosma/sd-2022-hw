package com.forum.user;

import com.forum.UrlMapping;
import com.forum.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(UrlMapping.USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @GetMapping(UrlMapping.REGULAR)
    public List<UserListDTO> allRegularUsers() {
        return userService.findAllRegularUsers();
    }

    @GetMapping(UrlMapping.NON_ADMIN)
    public List<UserListDTO> allNonAdminUsers() {
        return userService.findAllRegularUsers();
    }

    @PostMapping
    public UserListDTO create(@Valid @RequestBody UserListDTO userDTO) {
        return userService.create(userDTO);
    }

    @PutMapping(UrlMapping.USER_ID_PART)
    public UserListDTO edit(@PathVariable Long id, @Valid @RequestBody UserListDTO userDTO) {
        return userService.edit(id, userDTO);
    }

    @DeleteMapping(UrlMapping.USER_ID_PART)
    public UserListDTO delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
