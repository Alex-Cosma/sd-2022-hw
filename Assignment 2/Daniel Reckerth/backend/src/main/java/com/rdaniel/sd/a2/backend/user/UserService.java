package com.rdaniel.sd.a2.backend.user;

import com.rdaniel.sd.a2.backend.user.dto.RegularUserDto;
import com.rdaniel.sd.a2.backend.user.dto.UserListDto;
import com.rdaniel.sd.a2.backend.user.mapper.UserMapper;
import com.rdaniel.sd.a2.backend.user.model.Role;
import com.rdaniel.sd.a2.backend.user.model.RoleType;
import com.rdaniel.sd.a2.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public List<UserListDto> allUsersForList() {
    return userRepository.findAll()
        .stream().map(userMapper::userListDtoFromUser)
        .collect(toList());
  }

  public List<UserListDto> findAllRegularUsers() {
    final Role byName = roleRepository.findByName(RoleType.EMPLOYEE).
        orElseThrow(() -> new EntityNotFoundException("Role not found"));
    return userRepository.findAllByRolesNameIn(Set.of(byName.getName()))
        .stream()
        .map(userMapper::userListDtoFromUser)
        .collect(toList());
  }

  public UserListDto findById(final Long id) {
    return userRepository.findById(id)
        .map(userMapper::userListDtoFromUser)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
  }

  public UserListDto createRegularUser(RegularUserDto regularUserDto) {
    Role defaultRole = roleRepository.findByName(RoleType.EMPLOYEE)
        .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));

    final User user = User.builder()
        .username(regularUserDto.getName())
        .email(regularUserDto.getEmail())
        .password(passwordEncoder.encode(regularUserDto.getPassword()))
        .roles(Set.of(defaultRole))
        .build();

    return userMapper.userListDtoFromUser(userRepository.save(user));
  }

  public UserListDto updateRegularUser(Long id, RegularUserDto regularUserDto) {
    final User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    if(user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleType.ADMIN))) {
      throw new EntityNotFoundException("User is an admin");
    }

    user.setUsername(regularUserDto.getName());
    user.setEmail(regularUserDto.getEmail());

    return userMapper.userListDtoFromUser(userRepository.save(user));
  }

  public void deleteRegularUser(Long id) {
    final User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    if(user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleType.ADMIN))) {
      throw new EntityNotFoundException("User is an admin");
    }

    userRepository.deleteById(id);
  }
}
