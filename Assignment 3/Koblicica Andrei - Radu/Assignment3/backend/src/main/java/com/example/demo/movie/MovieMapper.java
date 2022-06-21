package com.example.demo.movie;

import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDTO toDto(Movie movie);
    Movie fromDto(MovieDTO movieDTO);

}
