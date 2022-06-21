package com.example.demo.report;

import com.example.demo.movie.MovieRepository;
import com.example.demo.movie.MovieService;
import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import com.example.demo.review.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.example.demo.TestCreationFactory.listOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private MovieService movieService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        reportService=new ReportService(movieService);
    }

    @Test
    void exportReport(){
        List<MovieDTO> movies=listOf(MovieDTO.class);
        when(movieService.findTopMovies()).thenReturn(movies);
        assertNotNull(reportService.exportReport());
    }
}
