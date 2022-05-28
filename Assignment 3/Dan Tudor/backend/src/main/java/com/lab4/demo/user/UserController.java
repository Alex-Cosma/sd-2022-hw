package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //CRUD used by admin user

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping(MAIL+ENTITY)
    public void sendMail(@PathVariable Long id) throws MessagingException {
        System.out.println("mail");
        userService.mail(id);
    }

    @PostMapping
    public void createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
    }

    @PutMapping(ENTITY)
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    //operations used by client user
    @PutMapping(BUY_QUANTITY)
    public void buy(@PathVariable("id") Long stockID, @PathVariable("quantity") int quantity, @RequestBody UserDTO userDTO){
        //System.out.println(userDTO.getId()+" "+userDTO.getUsername());
        userService.buy(stockID,quantity,userDTO);
    }

    @PutMapping(SELL_QUANTITY)
    public void sell(@PathVariable("id") Long stockID, @PathVariable("quantity") int quantity, @RequestBody UserDTO userDTO){
        userService.sell(stockID,quantity,userDTO);
    }
}
