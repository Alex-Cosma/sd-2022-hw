package com.project.clinic.user;

import com.project.clinic.treatment.TreatmentMapper;
import com.project.clinic.treatment.model.dto.TreatmentDTO;
import com.project.clinic.user.dto.UserListDTO;
import com.project.clinic.user.dto.UserMinimalDTO;
import com.project.clinic.user.dto.UserRegisterDTO;
import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.clinic.URLMapping.*;

@CrossOrigin(origins ="http://localhost:3000")
@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TreatmentMapper treatmentMapper;

    @GetMapping(FIND_ALL)
    public List<UserListDTO> allUsers() {
        return userService.findAll();
    }

    @GetMapping(DERMATOLOGISTS + FIND_ALL)
    public List<UserListDTO> allDermatologists() {
        List<UserListDTO> list = userService.allDermatologistsForList();
        return list;
    }

    @GetMapping(DERMATOLOGISTS + ID_PART + "/treatments")
    public List<TreatmentDTO> getTreatmentsOf(@PathVariable Long id) {
        User user = userService.findById(id);
        return user.getTreatments().stream()
                .map(treatmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(SKINCOLOR)
    public ResponseEntity<List<SkinColor>> findSkinColors(){
        return ResponseEntity.ok(userService.findAllSkinColors());
    }

    @PostMapping(ADD + DERMATOLOGISTS)
    public void create(@RequestBody UserMinimalDTO user) {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .id(user.getId())
                .password(user.getPassword())
                .name(user.getName())
                .points(0)
                .roles(Set.of("PROFESSIONAL"))
                .skinColor("BEIGE")
                .build();
        userService.create(userRegisterDTO);
    }

    @PostMapping(DELETE + USERS_ID_PART)
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping(UPDATE + USERS_ID_PART)
    public UserRegisterDTO edit(@PathVariable Long id, @RequestBody UserMinimalDTO user) {
        UserRegisterDTO userUpdate = UserRegisterDTO.builder()
                .id(user.getId())
                .password(user.getPassword())
                .name(user.getName())
                .points(0)
                .roles(new HashSet<>())
                .skinColor(null)
                .build();
        return userService.edit(id, userUpdate);
    }

    @GetMapping(FIND + USERS_ID_PART)
    public UserListDTO findUser(@PathVariable Long id){
        return userService.findDTOById(id);
    }

}
