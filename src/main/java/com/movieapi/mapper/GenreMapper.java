package com.movieapi.mapper;

import com.movieapi.dto.GenreDto;
import com.movieapi.entity.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper implements BaseMapper<Genre, GenreDto> {
    @Override
    public GenreDto toDTO(Genre genre) {
        if (genre == null) {
            throw new NullPointerException("Genre cannot be null");
        }
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    @Override
    public Genre toEntity(GenreDto genreDto) {
        if (genreDto == null) {
            throw new NullPointerException("GenreDto cannot be null");
        }
        return Genre.builder()
                .id(genreDto.getId())
                .name(genreDto.getName())
                .build();
    }
}