package com.movieapi.controller;

import com.movieapi.dto.GenreDto;
import com.movieapi.documentation.ApiDocsGenre;
import com.movieapi.service.GenreService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres")
@AllArgsConstructor
@Log4j2
public class GenreController implements ApiDocsGenre {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<GenreDto> createGenre(@Valid @RequestBody GenreDto genreDto) {
        log.info("---------- createGenre called ----------");
        GenreDto createdGenre = genreService.createGenre(genreDto);
        return new ResponseEntity<>(createdGenre, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<GenreDto>> getAllGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("---------- getAllGenres called with page: {}, size: {} ----------",
                page,
                size);
        Page<GenreDto> genres = genreService.getAllGenresWithPagination(page, size);
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable Long id) {
        log.info("---------- getGenreById called for id: {} ----------", id);
        GenreDto genre = genreService.getGenreById(id);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GenreDto> updateGenre(
            @PathVariable Long id,
            @Valid @RequestBody GenreDto genreDto) {
        log.info("---------- updateGenre called for id: {} ----------", id);
        GenreDto updatedGenre = genreService.updateGenre(id, genreDto);
        return new ResponseEntity<>(updatedGenre, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean force) {
        log.info("---------- deleteGenre called for id: {} with force: {} ----------",
                id,
                force);
        genreService.deleteGenre(id, force);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
