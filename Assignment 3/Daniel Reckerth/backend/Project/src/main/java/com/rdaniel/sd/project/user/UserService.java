package com.rdaniel.sd.project.user;

import com.rdaniel.sd.project.user.dto.UserDto;
import com.rdaniel.sd.project.user.mapper.UserMapper;
import com.rdaniel.sd.project.user.model.Role;
import com.rdaniel.sd.project.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import static com.rdaniel.sd.project.user.model.RoleType.*;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;

  public List<UserDto> findAllUsers() {
    return userRepository.findAll()
        .stream().map(userMapper::userToUserDto)
        .collect(toList());
  }

  public List<UserDto> findAllEmployees() {
    final Role byName = roleRepository.findByName(ROLE_EMPLOYEE).
        orElseThrow(() -> new EntityNotFoundException("Role not found"));
    return userRepository.findAllByRolesNameIn(Set.of(byName.getName()))
        .stream()
        .map(userMapper::userToUserDto)
        .collect(toList());
  }

  public UserDto findEmployeeById(final Long id) {
    final User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    if(user.getRoles().stream().anyMatch(role -> role.getName().equals(ROLE_CUSTOMER))) {
      throw new EntityNotFoundException("User is a customer");
    }

    return userMapper.userToUserDto(user);
  }

  public UserDto updateEmployee(Long id, UserDto regularUserDto) {
    final User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    if(user.getRoles().stream().anyMatch(role -> role.getName().equals(ROLE_CUSTOMER))) {
      throw new EntityNotFoundException("User is a customer");
    }

    user.setUsername(regularUserDto.getUsername());
    user.setEmail(regularUserDto.getEmail());

    return userMapper.userToUserDto(userRepository.save(user));
  }

  public void deleteEmployee(Long id) {
    final User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    if(user.getRoles().stream().anyMatch(role -> role.getName().equals(ROLE_CUSTOMER))) {
      throw new EntityNotFoundException("User is a customer");
    }

    userRepository.deleteById(id);
  }
}
