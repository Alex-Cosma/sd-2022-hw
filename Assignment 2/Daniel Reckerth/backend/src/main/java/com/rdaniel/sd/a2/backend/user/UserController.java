package com.rdaniel.sd.a2.backend.user;

import com.rdaniel.sd.a2.backend.user.dto.RegularUserDto;
import com.rdaniel.sd.a2.backend.user.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.rdaniel.sd.a2.backend.UrlMappings.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(USERS_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
  private final UserService userService;

  @GetMapping
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserListDto> getAllUsers() {
    return userService.allUsersForList();
  }

  @GetMapping(REGULAR_USERS_PATH)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserListDto> getAllRegularUsers() {
    try {
      return userService.findAllRegularUsers();
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @GetMapping(RESOURCE_BY_ID)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('ADMIN')")
  public UserListDto getUser(@PathVariable Long id) {
    try {
      return userService.findById(id);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @PostMapping
  @ResponseStatus(CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  public UserListDto createRegularUser(@RequestBody RegularUserDto regularUserDto) {
    try {
      return userService.createRegularUser(regularUserDto);
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @PutMapping(RESOURCE_BY_ID)
  @ResponseStatus(OK)
  @PreAuthorize("hasRole('ADMIN')")
  public UserListDto updateUser(@PathVariable Long id, @RequestBody RegularUserDto regularUserDto) {
    try {
      return userService.updateRegularUser(id, regularUserDto);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @DeleteMapping(RESOURCE_BY_ID)
  @ResponseStatus(NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteUser(@PathVariable Long id) {
    try {
      userService.deleteRegularUser(id);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(NOT_FOUND, e.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
