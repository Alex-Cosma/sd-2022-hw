package com.rdaniel.sd.a2.backend.user;

import com.rdaniel.sd.a2.backend.BaseControllerTest;
import com.rdaniel.sd.a2.backend.book.dto.BookDto;
import com.rdaniel.sd.a2.backend.user.dto.RegularUserDto;
import com.rdaniel.sd.a2.backend.user.dto.UserListDto;
import com.rdaniel.sd.a2.backend.user.model.Role;
import com.rdaniel.sd.a2.backend.user.model.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.rdaniel.sd.a2.backend.TestCreationFactory.*;
import static com.rdaniel.sd.a2.backend.UrlMappings.*;
import static com.rdaniel.sd.a2.backend.user.model.RoleType.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  @Mock
  private RoleRepository roleRepository;

  @BeforeEach
  protected void setUp() {
    super.setUp();
    userController = new UserController(userService);
    mockMvc = MockMvcBuilders.standaloneSetup(userController)
        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        .build();
  }

  @Test
  void allUsers() throws Exception {
    final List<UserListDto> dtos = listOf(UserListDto.class, 20);
    when(userService.allUsersForList()).thenReturn(dtos);

    mockMvc.perform(get(USERS_PATH))
        .andExpect(status().isOk())
        .andExpect(jsonContentToBe(dtos));

    verify(userService, times(1)).allUsersForList();
  }

  @Test
  void getAllRegularUsers() throws Exception {
    final List<UserListDto> dtos = listOf(UserListDto.class, 20);
    dtos.forEach(dto -> dto.setRoles(Set.of(EMPLOYEE.toString())));

    final Role employeeRole = Role.builder().id(1L).name(EMPLOYEE).build();
    when(roleRepository.findByName(any(RoleType.class))).thenReturn(Optional.ofNullable(employeeRole));
    when(userService.findAllRegularUsers()).thenReturn(dtos);

    mockMvc.perform(get(USERS_PATH + REGULAR_USERS_PATH))
        .andExpect(status().isOk())
        .andExpect(jsonContentToBe(dtos));

    verify(userService, times(1)).findAllRegularUsers();
  }

  @Test
  void getUser_validIdentifier_status200() throws Exception {
    final UserListDto dto = newUserListDto();
    dto.setRoles(Set.of(EMPLOYEE.toString()));
    when(userService.findById(any(Long.class))).thenReturn(dto);

    ResultActions response = performGetWithPathVariable(USERS_PATH + RESOURCE_BY_ID, dto.getId());
    response.andExpect(status().isOk())
        .andExpect(jsonContentToBe(dto));

    verify(userService, times(1)).findById(dto.getId());
  }

  @Test
  void getUser_invalidIdentifier_status404() throws Exception {
    when(userService.findById(any(Long.class))).thenThrow(EntityNotFoundException.class);

    ResultActions response = performGetWithPathVariable(USERS_PATH + RESOURCE_BY_ID, 1L);
    response.andExpect(status().isNotFound());

    verify(userService, times(1)).findById(any(Long.class));
  }

  @Test
  void createRegularUser() throws Exception {
    final RegularUserDto regularUserDto = newRegularUserDto();
    final UserListDto dto = UserListDto.builder()
        .id(regularUserDto.getId())
        .name(regularUserDto.getName())
        .roles(Set.of(EMPLOYEE.toString()))
        .build();
    final Role employeeRole = Role.builder().id(1L).name(EMPLOYEE).build();
    when(roleRepository.findByName(any(RoleType.class))).thenReturn(Optional.ofNullable(employeeRole));
    when(userService.createRegularUser(any(RegularUserDto.class))).thenReturn(dto);

    ResultActions response = performPostWithRequestBody(USERS_PATH, regularUserDto);
    response.andExpect(status().isCreated())
        .andExpect(jsonContentToBe(dto));

    verify(userService, times(1)).createRegularUser(any(RegularUserDto.class));
  }

  @Test
  void updateUser_validIdentifierAndContent_status200() throws Exception {
    final RegularUserDto regularUserDto = newRegularUserDto();
    final UserListDto dto = UserListDto.builder()
        .id(regularUserDto.getId())
        .name(regularUserDto.getName())
        .roles(Set.of(EMPLOYEE.toString()))
        .build();

    when(userService.updateRegularUser(any(Long.class), any(RegularUserDto.class))).thenReturn(dto);

    final ResultActions resultActions = performPutWithRequestBodyAndPathVariable(USERS_PATH + RESOURCE_BY_ID, regularUserDto, 1L);
    resultActions.andExpect(status().isOk())
        .andExpect(jsonContentToBe(dto));

    verify(userService, times(1)).updateRegularUser(any(Long.class), any(RegularUserDto.class));
  }

  @Test
  void updateUser_invalidIdentifier_status404() throws Exception {
    final RegularUserDto regularUserDto = newRegularUserDto();
    when(userService.updateRegularUser(any(Long.class), any(RegularUserDto.class))).thenThrow(EntityNotFoundException.class);

    final ResultActions resultActions = performPutWithRequestBodyAndPathVariable(USERS_PATH + RESOURCE_BY_ID, regularUserDto, 1L);
    resultActions.andExpect(status().isNotFound());

    verify(userService, times(1)).updateRegularUser(any(Long.class), any(RegularUserDto.class));
  }


  @Test
  void deleteUser_validIdentifier_status204() throws Exception {
    final ResultActions resultActions = performDeleteWithPathVariable(USERS_PATH + RESOURCE_BY_ID, 1L);
    resultActions.andExpect(status().isNoContent());

    verify(userService, times(1)).deleteRegularUser(any(Long.class));
  }

  @Test
  void deleteUser_invalidIdentifier_status404() throws Exception {
    doThrow(EntityNotFoundException.class).when(userService).deleteRegularUser(any(Long.class));

    final ResultActions resultActions = performDeleteWithPathVariable(USERS_PATH + RESOURCE_BY_ID, 1L);
    resultActions.andExpect(status().isNotFound());

    verify(userService, times(1)).deleteRegularUser(any(Long.class));
  }
}