package com.example.projectsd.court;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SportServiceTest {

    @InjectMocks
    private CourtService sportService;

    @Mock
    private CourtRepository sportRepository;

    @Mock
    private CourtMapper sportMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        //sportService = new CourtService(sportRepository, sportMapper);
    }

    @Test
    void create() {
    }

    @Test
    void findAll() {

    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
    }

    @Test
    void reserve() {
    }

    @Test
    void changeName() {
        //long sportId = randomLong();

    }
}