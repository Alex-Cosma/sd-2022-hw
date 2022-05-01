package com.example.backend.controller;

import com.example.backend.dto.user.SignInDto;
import com.example.backend.dto.user.UserCreationDto;
import com.example.backend.dto.user.UserGetDto;
import com.example.backend.service.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMappings.ADMINISTRATOR)
@CrossOrigin
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(UrlMappings.SIGN_IN)
    public boolean signIn(@RequestBody SignInDto signInDto) {
        return loginService.signIn(signInDto);
    }

    @PostMapping(UrlMappings.SIGN_UP)
    public UserGetDto signUp(@RequestBody UserCreationDto userCreationDto) {
        return loginService.signUp(userCreationDto);
    }
}
