package com.app.movie.model.mapper;

import com.app.movie.model.Movie;
import com.app.movie.model.dto.MovieDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDTO toDto(Movie movie);

    Movie fromDto(MovieDTO movieDTO);
}
