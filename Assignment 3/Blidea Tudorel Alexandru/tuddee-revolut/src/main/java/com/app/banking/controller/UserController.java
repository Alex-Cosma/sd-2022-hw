package com.app.banking.controller;

import com.app.banking.data.dto.model.CardDto;
import com.app.banking.data.dto.model.UserDto;
import com.app.banking.security.details.UserDetailsImpl;
import com.app.banking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.app.banking.URLMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:*")
public class UserController {
    private final UserService userService;

    @GetMapping("/my/details")
    public UserDto getMyDetails(Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return userService.findByUsername(userDetails.getUsername());
    }

    @GetMapping
    public List<UserDto> allUsers(){
        return userService.findAll();
    }

    @GetMapping(ID)
    public UserDto findById(@PathVariable Integer id){
        return userService.findById(id);
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto user){
        return userService.addUser(user);
    }

    @PatchMapping(ID)
    public UserDto update(@PathVariable Integer id, @RequestBody UserDto user) {
        return userService.update(id, user);
    }

    @DeleteMapping(ID)
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

}
