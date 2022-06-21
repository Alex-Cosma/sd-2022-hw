package com.example.demo.movie;

import com.example.demo.movie.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.TestCreationFactory.listOf;
import static com.example.demo.TestCreationFactory.newMovie;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MovieRepositoryIntegrationTest {

    @Autowired
    private MovieRepository repository;


    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }


    @Test
    public void findAll() {
        List<Movie> movies = listOf(Movie.class);
        repository.saveAll(movies);
        List<Movie> all = repository.findAll();
        assertEquals(movies.size(), all.size());
    }

    @Test
    public void findById() {
       Movie movie=newMovie();
       Movie savedMovie=repository.save(movie);
        Movie foundMovie = repository.findById(savedMovie.getId()).get();
        assertEquals(savedMovie.getTitle(), foundMovie.getTitle());
    }

    @Test
    public void findAllByTitleLike() {
        List<Movie> movies=new ArrayList<>();
        for(int i=0;i<4;i++){
            Movie movie=newMovie();
            if(i<2){
                movie.setTitle("random"+i);
            }
            movies.add(movie);
        }
        repository.saveAll(movies);
        List<Movie> foundMovies = repository.findAllByTitleLike("%random%");

        assertEquals(2, foundMovies.size());
    }


    @Test
    public void deleteById(){
        Movie movie=newMovie();
        Movie savedMovie=repository.save(movie);
        assertEquals(1,repository.findAll().size());
        repository.deleteById(savedMovie.getId());
        assertEquals(0,repository.findAll().size());
    }
}
