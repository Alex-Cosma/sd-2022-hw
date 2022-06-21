package com.example.projectsd.user;

import com.example.projectsd.court.CourtService;
import com.example.projectsd.court.model.dto.CourtDTO;
import com.example.projectsd.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.projectsd.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CourtService courtService;

    @GetMapping
    public List<UserListDTO> allUsers(){
        return userService.allUsersForList();
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @GetMapping(COURTS_ADMIN)
    public List<CourtDTO> allCourtsForAdmin(){
        return courtService.findAll();
    }



}
