package com.movieapi.mapper;

import com.movieapi.dto.MovieDto;
import com.movieapi.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper implements BaseMapper<Movie, MovieDto> {
    @Override
    public MovieDto toDTO(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("Movie cannot be null");
        }
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .releaseYear(movie.getReleaseYear())
                .duration(movie.getDuration())
                .build();
    }

    @Override
    public Movie toEntity(MovieDto movieDto) {
        if (movieDto == null) {
            throw new NullPointerException("MovieDto cannot be null");
        }
        return Movie.builder()
                .id(movieDto.getId())
                .title(movieDto.getTitle())
                .releaseYear(movieDto.getReleaseYear())
                .duration(movieDto.getDuration())
                .genres(new java.util.HashSet<>())
                .actors(new java.util.HashSet<>())
                .build();
    }
}