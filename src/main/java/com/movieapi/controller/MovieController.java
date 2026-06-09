package com.movieapi.controller;

import com.movieapi.dto.MovieDto;
import com.movieapi.documentation.ApiDocsMovie;
import com.movieapi.service.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
@Log4j2
public class MovieController implements ApiDocsMovie {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@Valid @RequestBody MovieDto movieDto) {
        log.info("---------- createMovie called ----------");
        MovieDto createdMovie = movieService.createMovie(movieDto);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<MovieDto>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("---------- getAllMovies called with page: {}, size: {} ----------", page, size);
        Page<MovieDto> movies = movieService.getAllMoviesWithPagination(page, size);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        log.info("---------- getMovieById called for id: {} ----------", id);
        MovieDto movie = movieService.getMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieDto movieDto) {
        log.info("---------- updateMovie called for id: {} ----------", id);
        MovieDto updatedMovie = movieService.updateMovie(id, movieDto);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.info("---------- deleteMovie called for id: {} ----------", id);
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-genre")
    public ResponseEntity<Page<MovieDto>> getMoviesByGenre(
            @RequestParam Long genreId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("---------- getMoviesByGenre called for genreId: {}, page: {}, size: {} ----------",
                genreId, page, size);
        Page<MovieDto> movies = movieService.getMoviesByGenreWithPagination(genreId, page, size);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/by-year")
    public ResponseEntity<List<MovieDto>> getMoviesByYear(@RequestParam Integer year) {
        log.info("---------- getMoviesByYear called for year: {} ----------", year);
        List<MovieDto> movies = movieService.getMoviesByYear(year);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/by-actor")
    public ResponseEntity<Page<MovieDto>> getMoviesByActor(
            @RequestParam Long actorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("---------- getMoviesByActor called for actorId: {}, page: {}, size: {} ----------",
                actorId, page, size);
        Page<MovieDto> movies = movieService.getMoviesByActorWithPagination(actorId, page, size);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/{movieId}/actors")
    public ResponseEntity<List<Long>> getActorIdsInMovie(@PathVariable Long movieId) {
        log.info("---------- getActorIdsInMovie called for movieId: {} ----------", movieId);
        List<Long> actorIds = movieService.getActorIdsInMovie(movieId);
        return new ResponseEntity<>(actorIds, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MovieDto>> searchMoviesByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("---------- searchMoviesByTitle called for title: {}, page: {}, size: {} ----------",
                title, page, size);
        Page<MovieDto> movies = movieService.searchMoviesByTitleWithPagination(title, page, size);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}