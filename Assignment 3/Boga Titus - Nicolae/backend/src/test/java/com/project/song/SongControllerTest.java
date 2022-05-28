package com.project.song;

import com.project.BaseControllerTest;
import com.project.TestCreationFactory;

import com.project.UrlMapping;
import com.project.song.model.dto.SongDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SongControllerTest extends BaseControllerTest {

    @InjectMocks
    private SongController controller;

    @Mock
    private SongService songService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new SongController(songService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }


    @Test
    void allSongs() throws Exception {
        List<SongDTO> songs = TestCreationFactory.listOf(com.project.song.model.Song.class);
        when(songService.findAll()).thenReturn(songs);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.SONGS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(songs));

    }

    @Test
    void allSongsOnline() throws Exception {

        ArrayList<SongDTO> music= new ArrayList<>();
        try {
            System.out.println("Songs online");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://spotify23.p.rapidapi.com/search/?q=%3CREQUIRED%3E&type=multi&offset=0&limit=10&numberOfTopResults=5"))
                    .header("X-RapidAPI-Host", "spotify23.p.rapidapi.com")
                    .header("X-RapidAPI-Key", "6aed63ec0cmsh6fbb66974da5728p182777jsn4fce90f2afe4")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject responseJsonObj = new JSONObject(response.body().toString());
            JSONArray songs=responseJsonObj.getJSONObject("tracks").getJSONArray("items");
            for(int i=0;i<10;++i)
            {
                String name=songs.getJSONObject(i).getJSONObject("data").getString("name");
                String artist= songs.getJSONObject(i).getJSONObject("data").getJSONObject("artists").getJSONArray("items").getJSONObject(0).getJSONObject("profile").getString("name");
                SongDTO songDTO= new SongDTO(0L,name,artist);
                music.add(songDTO);
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }

        when(songService.findAllOnline()).thenReturn(music);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.SONGS + UrlMapping.SONGS_ONLINE));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(music));

    }


    @Test
    void create() throws Exception {
        SongDTO reqSong = SongDTO.builder()
                .id(TestCreationFactory.randomLong())
                .title(TestCreationFactory.randomString())
                .artist(TestCreationFactory.randomString())
                .build();

        when(songService.create(reqSong)).thenReturn(reqSong);

        ResultActions result = performPostWithRequestBody(UrlMapping.SONGS, reqSong);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqSong));
    }


    @Test
    void edit() throws Exception {
        long id = TestCreationFactory.randomLong();
        SongDTO reqSong = SongDTO.builder()
                .id(id)
                .title(TestCreationFactory.randomString())
                .artist(TestCreationFactory.randomString())
                .build();

        when(songService.edit(id, reqSong)).thenReturn(reqSong);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(UrlMapping.SONGS + UrlMapping.SONG_ENTITY, reqSong, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqSong));
    }


    @Test
    void delete() throws Exception {
        long id = TestCreationFactory.randomLong();
        doNothing().when(songService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(UrlMapping.SONGS + UrlMapping.SONG_ENTITY, id);
        verify(songService, times(1)).delete(id);

        result.andExpect(status().isOk());

    }




}