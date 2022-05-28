package com.example.leaguecomp.user;

import com.example.leaguecomp.BaseControllerTest;
import com.example.leaguecomp.TestCreationFactory;
import com.example.leaguecomp.summoner.SummonerService;
import com.example.leaguecomp.summoner.dto.SummonerDTO;
import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import com.example.leaguecomp.summoner.model.Summoner;
import com.example.leaguecomp.user.dto.UserListDTO;
import com.example.leaguecomp.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static com.example.leaguecomp.TestCreationFactory.*;
import static com.example.leaguecomp.UrlMappings.ENTITY;
import static com.example.leaguecomp.UrlMappings.USERS;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest extends BaseControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    @Mock
    private SummonerService summonerService;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        controller = new UserController(service,summonerService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }
    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(service.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        User reqUser = User.builder().username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();

        when(service.create(reqUser)).thenReturn(reqUser);

        ResultActions result = performPostWithRequestBody("/api/users/create", reqUser);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void followSummoner() throws Exception {
        SummonerDTO summoner = SummonerDTO.builder()
                .name("name")
                .league("IRON")
                .build();
        User reqUser = User.builder().username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
        when(summonerService.create(summoner)).thenReturn(summoner);
        when(service.create(reqUser)).thenReturn(reqUser);

        ResultActions resultActions = performPostWithRequestBodyAndPathVariables("http://localhost:8080/api/users/follow/{name}",reqUser.getId(),summoner.getName());
        resultActions.andExpect(status().isOk());

    }

    @Test
    void edit() throws  Exception{
        long id = randomLong();
        UserListDTO userListDTO = UserListDTO.builder()
                .email("somemail@gmail.com")
                .name("aaaaa")
                .id(id)
                .roles(Set.of("ADMINISTRATOR"))
                .build();
        when(service.edit(id,userListDTO)).thenReturn(userListDTO);
        ResultActions resultActions = performPatchWithRequestBodyAndPathVariables("http://localhost:8080/api/users/edit/{id}",userListDTO,id);
                resultActions.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        User reqUser = User.builder().username(randomString())
                .id(id)
                .email(randomEmail())
                .password(randomString())
                .build();

        when(service.create(reqUser)).thenReturn(reqUser);
        ResultActions resultActions = performDeleteWIthPathVariables("http://localhost:8080/api/users/delete/{id}",id);
        resultActions.andExpect(status().isOk());
    }


}