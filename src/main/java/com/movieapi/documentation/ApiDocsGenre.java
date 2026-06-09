package com.movieapi.documentation;

import com.movieapi.dto.GenreDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Tag(name = "genre", description = "API for managing genre reference data")
public interface ApiDocsGenre {

    @Operation(
            summary = "Create a new genre",
            description = "Add a new genre to the reference data.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Genre created successfully",
                    content = @Content(schema = @Schema(implementation = GenreDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<GenreDto> createGenre(GenreDto genreDto);

    @Operation(
            summary = "Get all genres",
            description = "Retrieve a list of all supported genres with pagination support.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of genres retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class))),
    })
    ResponseEntity<Page<GenreDto>> getAllGenres(int page, int size);

    @Operation(
            summary = "Get genre by ID",
            description = "Retrieve detailed information about a specific genre by its unique identifier.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Genre retrieved successfully",
                    content = @Content(schema = @Schema(implementation = GenreDto.class))),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    ResponseEntity<GenreDto> getGenreById(Long id);

    @Operation(
            summary = "Update an existing genre",
            description = "Partially modify the details of an existing genre by its unique identifier using PATCH.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Genre updated successfully",
                    content = @Content(schema = @Schema(implementation = GenreDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    ResponseEntity<GenreDto> updateGenre(Long id, GenreDto genreDto);

    @Operation(
            summary = "Delete a genre",
            description = "Remove a genre from the reference data by its unique identifier. If the genre has associated movies, deletion will fail unless force parameter is true.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Genre deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Cannot delete genre because it has associated movies"),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    ResponseEntity<Void> deleteGenre(Long id, boolean force);
}