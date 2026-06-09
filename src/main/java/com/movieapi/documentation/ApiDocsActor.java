package com.movieapi.documentation;

import com.movieapi.dto.ActorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "actor", description = "API for managing actor data")
public interface ApiDocsActor {

    @Operation(
            summary = "Create a new actor",
            description = "Add a new actor to the database with their name and birth date.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Actor created successfully",
                    content = @Content(schema = @Schema(implementation = ActorDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<ActorDto> createActor(ActorDto actorDto);

    @Operation(
            summary = "Get all actors",
            description = "Retrieve a list of all actors in the database with pagination support.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of actors retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class))),
    })
    ResponseEntity<Page<ActorDto>> getAllActors(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);

    @Operation(
            summary = "Get actor by ID",
            description = "Retrieve detailed information about a specific actor by its unique identifier.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Actor retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ActorDto.class))),
            @ApiResponse(responseCode = "404", description = "Actor not found")
    })
    ResponseEntity<ActorDto> getActorById(Long id);

    @Operation(
            summary = "Update an existing actor",
            description = "Partially modify the details of an existing actor by its unique identifier using PATCH.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Actor updated successfully",
                    content = @Content(schema = @Schema(implementation = ActorDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Actor not found")
    })
    ResponseEntity<ActorDto> updateActor(Long id, ActorDto actorDto);

    @Operation(
            summary = "Delete an actor",
            description = "Remove an actor from the database by its unique identifier. If the actor has associated movies, deletion will fail unless force parameter is true.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Actor deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Cannot delete actor because it has associated movies"),
            @ApiResponse(responseCode = "404", description = "Actor not found")
    })
    ResponseEntity<Void> deleteActor(Long id, boolean force);

    @Operation(
            summary = "Get actors by name",
            description = "Retrieve a list of actors filtered by name (case-insensitive partial match).")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Actors retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Invalid name parameter")
    })
    ResponseEntity<List<ActorDto>> getActorsByName(String name);
}