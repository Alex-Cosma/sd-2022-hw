package com.user;

import com.group.model.dto.GroupDto;
import com.user.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin
    @GetMapping
    public List<UserListDto> allUsers() {
        return userService.allUsersForList();
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserListDto user) {
        return userService.create(user);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @CrossOrigin
    @PutMapping(ENTITY)
    public UserListDto edit(@PathVariable Long id, @RequestBody UserListDto user) {

        return userService.edit(id, user);
    }

    @CrossOrigin
    @PatchMapping(ENTITY)
    public UserListDto addFriend(@PathVariable Long id,@RequestBody UserListDto user) {
        return userService.addFriend(user.getId(),id);
    }

}