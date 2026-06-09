package com.movieapi.documentation;

import com.movieapi.dto.MovieDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "movie", description = "API for managing movies")
@RequestMapping("/api/movies")
public interface ApiDocsMovie {

    @PostMapping
    @Operation(
            operationId = "createMovie",
            summary = "Create a new movie",
            description = "Create a new movie with title, release year, duration, genre IDs and actor IDs."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Movie created successfully",
                    content = @Content(schema = @Schema(implementation = MovieDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<MovieDto> createMovie(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Movie data to create",
                    required = true
            )
            @RequestBody MovieDto movieDto
    );

    @GetMapping
    @Operation(
            operationId = "getAllMovies",
            summary = "Get all movies",
            description = "Retrieve paginated list of all movies."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Movies retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class))
            )
    })
    ResponseEntity<Page<MovieDto>> getAllMovies(
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size
    );

    @GetMapping("/{id}")
    @Operation(
            operationId = "getMovieById",
            summary = "Get movie by ID",
            description = "Retrieve a movie by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Movie found successfully",
                    content = @Content(schema = @Schema(implementation = MovieDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    ResponseEntity<MovieDto> getMovieById(
            @Parameter(description = "Unique movie identifier", required = true, example = "1")
            @PathVariable Long id
    );

    @PatchMapping("/{id}")
    @Operation(
            operationId = "updateMovie",
            summary = "Update movie by ID",
            description = "Partially update movie fields using PATCH."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Movie updated successfully",
                    content = @Content(schema = @Schema(implementation = MovieDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    ResponseEntity<MovieDto> updateMovie(
            @Parameter(description = "Unique movie identifier", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Movie data to update",
                    required = true
            )
            @RequestBody MovieDto movieDto
    );

    @DeleteMapping("/{id}")
    @Operation(
            operationId = "deleteMovie",
            summary = "Delete movie by ID",
            description = "Delete movie by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Movie deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    ResponseEntity<Void> deleteMovie(
            @Parameter(description = "Unique movie identifier", required = true, example = "1")
            @PathVariable Long id
    );

    @GetMapping("/by-genre")
    @Operation(
            operationId = "getMoviesByGenre",
            summary = "Get movies by genre",
            description = "Retrieve paginated list of movies filtered by genre ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Movies retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class))
            ),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    ResponseEntity<Page<MovieDto>> getMoviesByGenre(
            @Parameter(description = "Genre identifier", required = true, example = "1")
            @RequestParam Long genreId,
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size
    );

    @GetMapping("/by-year")
    @Operation(
            operationId = "getMoviesByYear",
            summary = "Get movies by release year",
            description = "Retrieve list of movies filtered by release year."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Movies retrieved successfully",
                    content = @Content(schema = @Schema(implementation = MovieDto.class))
            )
    })
    ResponseEntity<List<MovieDto>> getMoviesByYear(
            @Parameter(description = "Release year", required = true, example = "2023")
            @RequestParam Integer year
    );

    @GetMapping("/by-actor")
    @Operation(
            operationId = "getMoviesByActor",
            summary = "Get movies by actor",
            description = "Retrieve paginated list of movies where the actor participated."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Movies retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class))
            ),
            @ApiResponse(responseCode = "404", description = "Actor not found")
    })
    ResponseEntity<Page<MovieDto>> getMoviesByActor(
            @Parameter(description = "Actor identifier", required = true, example = "1")
            @RequestParam Long actorId,
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size
    );

    @GetMapping("/{movieId}/actors")
    @Operation(
            operationId = "getActorIdsInMovie",
            summary = "Get actor IDs of a movie",
            description = "Retrieve list of actor IDs participating in the movie."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Actor IDs retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Long[].class))
            ),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    ResponseEntity<List<Long>> getActorIdsInMovie(
            @Parameter(description = "Unique movie identifier", required = true, example = "1")
            @PathVariable Long movieId
    );

    @GetMapping("/search")
    @Operation(
            operationId = "searchMoviesByTitle",
            summary = "Search movies by title",
            description = "Retrieve paginated list of movies that match the search title."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Movies retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class))
            )
    })
    ResponseEntity<Page<MovieDto>> searchMoviesByTitle(
            @Parameter(description = "Movie title or partial title", required = true, example = "Inception")
            @RequestParam String title,
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size
    );
}