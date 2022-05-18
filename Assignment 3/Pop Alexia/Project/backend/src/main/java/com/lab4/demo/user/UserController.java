package com.lab4.demo.user;

import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

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
           throw new ConstraintViolationException("Username already exists", Set.of());
        }else if(userService.existsByEmail(user.getEmail())) {
            throw  new ConstraintViolationException("Email already exists", Set.of());
        }else if(user.getPassword() == null){
            throw new ConstraintViolationException("Password can't be empty",Set.of());
        }else if(user.getPassword().length() < 6){
            throw new ConstraintViolationException("Password must be at least 6 characters long", Set.of());
        }
        userService.create(user);
        return ResponseEntity.ok(new MessageResponse("User created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody UserDTO user) {
        if(!userService.findById(id).getUsername().equals(user.getUsername())){
            if(userService.existsByUsername(user.getUsername())) {
                throw new ConstraintViolationException("Username already exists", Set.of());
            }
        }else if(!userService.findById(id).getEmail().equals(user.getEmail())){
            if(userService.existsByEmail(user.getEmail())) {
                throw  new ConstraintViolationException("Email already exists", Set.of());
            }
        }
        userService.edit(id,user);
        return ResponseEntity.ok(new MessageResponse("User edited successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse(e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse(e.getMessage()));
    }
}
