package com.movieapi.service;

import com.movieapi.entity.Genre;
import com.movieapi.entity.Movie;
import com.movieapi.dto.GenreDto;
import com.movieapi.mapper.GenreMapper;
import com.movieapi.repository.GenreRepository;
import com.movieapi.repository.MovieRepository;
import com.movieapi.exception.ResourceNotFoundException;
import com.movieapi.exception.ResourceHasRelationsException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final GenreMapper genreMapper;

    public GenreDto createGenre(GenreDto genreDto) {
        Genre genre = genreMapper.toEntity(genreDto);
        Genre savedGenre = genreRepository.save(genre);
        return genreMapper.toDTO(savedGenre);
    }

    public List<GenreDto> getAllGenres() {
        return getAllGenresWithPagination(0, 10).getContent();
    }

    public Page<GenreDto> getAllGenresWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Genre> genresPage = genreRepository.findAll(pageable);
        return genresPage.map(genreMapper::toDTO);
    }

    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", id));
        return genreMapper.toDTO(genre);
    }

    public GenreDto updateGenre(Long id, GenreDto genreDto) {
        Genre existingGenre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", id));

        if (genreDto.getName() != null) {
            existingGenre.setName(genreDto.getName());
        }

        Genre updatedGenre = genreRepository.save(existingGenre);
        return genreMapper.toDTO(updatedGenre);
    }

    public void deleteGenre(Long id) {
        deleteGenre(id, false);
    }

    public void deleteGenre(Long id, boolean force) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", id));

        List<Movie> associatedMovies = movieRepository.findByGenres_Id(id);

        if (!associatedMovies.isEmpty()) {
            if (!force) {
                throw new ResourceHasRelationsException(
                    String.format("Cannot delete genre '%s' because it has %d associated movies",
                                  genre.getName(), associatedMovies.size()));
            } else {
                for (Movie movie : associatedMovies) {
                    if (movie.getGenres() != null) {
                        movie.getGenres().remove(genre);
                        movieRepository.save(movie);
                    }
                }
            }
        }

        genreRepository.deleteById(id);
    }
}
