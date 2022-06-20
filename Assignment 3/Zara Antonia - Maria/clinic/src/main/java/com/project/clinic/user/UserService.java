package com.project.clinic.user;

import com.project.clinic.security.AuthService;
import com.project.clinic.skin_color.SkinColorRepository;
import com.project.clinic.skin_color.SkinColorService;
import com.project.clinic.user.dto.UserListDTO;
import com.project.clinic.user.dto.UserRegisterDTO;
import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SkinColorService skinColorService;
    private final UserMapper userMapper;
    private final AuthService authService;

    public List<UserListDTO> allDermatologistsForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .filter(u -> u.getRoles().contains("PROFESSIONAL"))
                .collect(toList());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserListDTO findDTOById(Long id){
        return userMapper.userListDtoFromUser(
                userRepository.findById(id).get()
        );
    }


    public List<UserListDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::userListDtoFromUser)
                .collect(Collectors.toList());
    }

    public void create(UserRegisterDTO user) {
        authService.createNewUserFromDTO(user);
    }

    public UserRegisterDTO edit(Long id, UserRegisterDTO user) {
        User actUser = findById(id);
        actUser.setUsername(user.getName());
        UserRegisterDTO urd =  userMapper.userRegisterDtoFromUser(
                userRepository.save(actUser));
        return urd;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<SkinColor> findAllSkinColors() {
        List<SkinColor> skinColors =  skinColorService.findAll();
        return skinColors;
    }

    public void buy(Long id, Long productId, int price) {
        userRepository.buy(id, price);
    }
}