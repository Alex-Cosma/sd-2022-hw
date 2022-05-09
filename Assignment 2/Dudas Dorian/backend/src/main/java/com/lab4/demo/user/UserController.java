package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping(EMPLOYEES)
    public List<UserListDTO> allEmployees() {
        return userService.findAllEmployees();
    }

    @PostMapping
    public UserListDTO create(@Valid @RequestBody UserListDTO userDTO) {
        return userService.create(userDTO);
    }

    @PutMapping(USER_ID_PART)
    public UserListDTO edit(@PathVariable Long id, @Valid @RequestBody UserListDTO userDTO) {
        return userService.edit(id, userDTO);
    }

    @DeleteMapping(USER_ID_PART)
    public UserListDTO delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
