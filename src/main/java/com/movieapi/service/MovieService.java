package com.movieapi.service;

import com.movieapi.entity.Actor;
import com.movieapi.entity.Genre;
import com.movieapi.entity.Movie;
import com.movieapi.dto.MovieDto;
import com.movieapi.mapper.MovieMapper;
import com.movieapi.repository.ActorRepository;
import com.movieapi.repository.GenreRepository;
import com.movieapi.repository.MovieRepository;
import com.movieapi.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final MovieMapper movieMapper;

    public MovieDto createMovie(MovieDto movieDto) {
        Movie movie = movieMapper.toEntity(movieDto);

        if (movieDto.getGenreIds() != null && !movieDto.getGenreIds().isEmpty()) {
            Set<Genre> genres = new HashSet<>(genreRepository.findAllById(movieDto.getGenreIds()));
            if (movie.getGenres() != null) {
                movie.getGenres().addAll(genres);
            } else {
                movie.setGenres(genres);
            }
        }

        if (movieDto.getActorIds() != null && !movieDto.getActorIds().isEmpty()) {
            Set<Actor> actors = new HashSet<>(actorRepository.findAllById(movieDto.getActorIds()));
            if (movie.getActors() != null) {
                movie.getActors().addAll(actors);
            } else {
                movie.setActors(actors);
            }
        }

        Movie savedMovie = movieRepository.save(movie);
        return convertToDtoWithIds(savedMovie);
    }

    public List<MovieDto> getAllMovies() {
        return getAllMoviesWithPagination(0, 10).getContent();
    }

    public Page<MovieDto> getAllMoviesWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviesPage = movieRepository.findAll(pageable);
        return moviesPage.map(this::convertToDtoWithIds);
    }

    public MovieDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", id));
        return convertToDtoWithIds(movie);
    }

    public MovieDto updateMovie(Long id, MovieDto movieDto) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", id));

        if (movieDto.getTitle() != null) {
            existingMovie.setTitle(movieDto.getTitle());
        }
        if (movieDto.getReleaseYear() != null) {
            existingMovie.setReleaseYear(movieDto.getReleaseYear());
        }
        if (movieDto.getDuration() != null) {
            existingMovie.setDuration(movieDto.getDuration());
        }

        if (movieDto.getGenreIds() != null) {
            Set<Genre> genres = new HashSet<>(genreRepository.findAllById(movieDto.getGenreIds()));
            if (existingMovie.getGenres() != null) {
                existingMovie.getGenres().clear();
                existingMovie.getGenres().addAll(genres);
            } else {
                existingMovie.setGenres(genres);
            }
        }

        if (movieDto.getActorIds() != null) {
            Set<Actor> actors = new HashSet<>(actorRepository.findAllById(movieDto.getActorIds()));
            if (existingMovie.getActors() != null) {
                existingMovie.getActors().clear();
                existingMovie.getActors().addAll(actors);
            } else {
                existingMovie.setActors(actors);
            }
        }

        Movie updatedMovie = movieRepository.save(existingMovie);
        return convertToDtoWithIds(updatedMovie);
    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie", id);
        }
        movieRepository.deleteById(id);
    }

    public List<MovieDto> getMoviesByGenre(Long genreId) {
        return getMoviesByGenreWithPagination(genreId, 0, 10).getContent();
    }

    public Page<MovieDto> getMoviesByGenreWithPagination(Long genreId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviesPage = movieRepository.findByGenres_Id(genreId, pageable);
        return moviesPage.map(this::convertToDtoWithIds);
    }

    public List<MovieDto> getMoviesByYear(Integer releaseYear) {
        List<Movie> allMovies = movieRepository.findAll();
        List<Movie> filteredMovies = allMovies.stream()
                .filter(movie -> movie.getReleaseYear() != null && movie.getReleaseYear().equals(releaseYear))
                .toList();
        return filteredMovies.stream()
                .map(this::convertToDtoWithIds)
                .collect(Collectors.toList());
    }

    public List<MovieDto> getMoviesByActor(Long actorId) {
        return getMoviesByActorWithPagination(actorId, 0, 10).getContent();
    }

    public Page<MovieDto> getMoviesByActorWithPagination(Long actorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviesPage = movieRepository.findByActors_Id(actorId, pageable);
        return moviesPage.map(this::convertToDtoWithIds);
    }

    public List<Long> getActorIdsInMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", movieId));

        return movie.getActors().stream()
                .map(Actor::getId)
                .collect(Collectors.toList());
    }

    public List<MovieDto> searchMoviesByTitle(String title) {
        return searchMoviesByTitleWithPagination(title, 0, 10).getContent();
    }

    public Page<MovieDto> searchMoviesByTitleWithPagination(String title, int page, int size) {
        List<Movie> allMovies = movieRepository.findAll();
        List<Movie> filteredMovies = allMovies.stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();

        int start = page * size;
        int end = Math.min(start + size, filteredMovies.size());

        if (start >= filteredMovies.size()) {
            return new PageImpl<>(new java.util.ArrayList<>());
        }

        List<MovieDto> pagedMovies = filteredMovies.subList(start, end).stream()
                .map(this::convertToDtoWithIds)
                .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) filteredMovies.size() / size);

        return new PageImpl<>(pagedMovies,
                PageRequest.of(page, size),
                filteredMovies.size());
    }

    private MovieDto convertToDtoWithIds(Movie movie) {
        Set<Long> genreIds = null;
        if (movie.getGenres() != null) {
            genreIds = movie.getGenres().stream()
                    .map(Genre::getId)
                    .collect(Collectors.toSet());
        }

        Set<Long> actorIds = null;
        if (movie.getActors() != null) {
            actorIds = movie.getActors().stream()
                    .map(Actor::getId)
                    .collect(Collectors.toSet());
        }

        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .releaseYear(movie.getReleaseYear())
                .duration(movie.getDuration())
                .genreIds(genreIds)
                .actorIds(actorIds)
                .build();
    }
}
