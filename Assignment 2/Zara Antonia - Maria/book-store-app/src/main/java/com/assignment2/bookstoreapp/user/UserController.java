package com.assignment2.bookstoreapp.user;
import com.assignment2.bookstoreapp.user.dto.UserListDTO;
import com.assignment2.bookstoreapp.user.dto.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.assignment2.bookstoreapp.URLMapping.*;

@CrossOrigin
@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(FIND_ALL)
    public List<UserListDTO> allUsers() {
        List<UserListDTO> list = userService.allUsersForList();
        for(UserListDTO userListDTO: list){
            System.out.println(userListDTO);
        }
        return userService.allUsersForList();
    }

    @PostMapping(ADD)
    public void create(@RequestBody UserRegisterDTO user) {
        System.out.println(user.getRoles());
        System.out.println(user.getPassword());
        System.out.println(user.getName());
        userService.create(user);
    }

    @PostMapping(DELETE + USERS_ID_PART)
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping(UPDATE + USERS_ID_PART)
    public UserRegisterDTO edit(@PathVariable Long id, @RequestBody UserRegisterDTO userUpdate) {
        return userService.edit(id, userUpdate);
    }

    @GetMapping(FIND + USERS_ID_PART)
    public UserListDTO findUser(@PathVariable Long id){
        return userService.findDTOById(id);
    }

}