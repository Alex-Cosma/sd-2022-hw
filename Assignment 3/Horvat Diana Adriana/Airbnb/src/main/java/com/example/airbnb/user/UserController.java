package com.example.airbnb.user;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static com.example.airbnb.user.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @CrossOrigin
//    @GetMapping
//    public List<UserListDTO> allUsers() {
//        return userService.allUsersForList();
//    }
//
//    @CrossOrigin
//    @PostMapping
//    public ResponseEntity<?> create(@Valid @RequestBody UserListDTO user) {
//        return userService.create(user);
//    }
//
//    @CrossOrigin
//    @DeleteMapping(ENTITY)
//    public void delete(@PathVariable Long id) {
//        userService.delete(id);
//    }
//
//    @CrossOrigin
//    @PutMapping(ENTITY)
//    public UserListDTO edit(@PathVariable Long id, @RequestBody UserListDTO user) {
//        return userService.edit(id, user);
//    }

    @CrossOrigin
    @GetMapping(USER_ACCOMMODATIONS)
    public Set<Accommodation> getUserAccommodations(@PathVariable Long id){
        return userService.getUserAccommodations(id);
    }

    @CrossOrigin
    @GetMapping(USER_BOOKINGS)
    public Set<Booking> getUserBookings(@PathVariable Long id){
        return userService.getUsersBookings(id);
    }

    @CrossOrigin
    @PatchMapping(ENTITY)
    public void addUserHostRole(@PathVariable Long id, @RequestBody UserListDTO user){
        userService.addUserHostRole(id);
    }
}
