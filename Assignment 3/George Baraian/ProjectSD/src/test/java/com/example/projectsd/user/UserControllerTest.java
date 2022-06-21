package com.example.projectsd.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class UserControllerTest{

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp(){
    }

    @Test
    void allUsers() {
    }

    @Test
    void deleteUser() {
    }
}