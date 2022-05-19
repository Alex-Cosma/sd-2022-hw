package com.lab4.demo.user;

import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.lab4.demo.UrlMapping.USER;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> allUsers() {
        return userService.findAll();
    }

    @GetMapping("/filter/{filter}")
    public List<UserDTO> filterUsers(@PathVariable String filter) {
        return userService.filterUsers(filter);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDTO user) {
        if(userService.existsByUsername(user.getUsername())) {
           return ResponseEntity.badRequest().body(new MessageResponse("Username already exists"));
        }else if(userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email already exists"));
        }else if(user.getPassword() == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Password is required"));
        }else if(user.getPassword().length() < 6){
            return ResponseEntity.badRequest().body(new MessageResponse("Password must be at least 6 characters"));
        }
        try {
            userService.create(user);
            return ResponseEntity.ok(new MessageResponse("User created successfully"));
        }catch(ConstraintViolationException e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getConstraintViolations().iterator().next().getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody UserDTO user) {
        if(!userService.findById(id).getUsername().equals(user.getUsername())){
            if(userService.existsByUsername(user.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Username already exists"));
            }
        }else if(!userService.findById(id).getEmail().equals(user.getEmail())){
            if(userService.existsByEmail(user.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Email already exists"));
            }
        }
        try {
            userService.edit(id, user);
            return ResponseEntity.ok(new MessageResponse("User edited successfully"));
        }catch(ConstraintViolationException e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getConstraintViolations().iterator().next().getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    }
}
