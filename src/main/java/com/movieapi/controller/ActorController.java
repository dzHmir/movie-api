package com.movieapi.controller;

import com.movieapi.dto.ActorDto;
import com.movieapi.documentation.ApiDocsActor;
import com.movieapi.service.ActorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
@AllArgsConstructor
@Log4j2
public class ActorController implements ApiDocsActor {

    private final ActorService actorService;

    @PostMapping
    public ResponseEntity<ActorDto> createActor(@Valid @RequestBody ActorDto actorDto) {
        log.info("---------- createActor called ----------");
        ActorDto createdActor = actorService.createActor(actorDto);
        return new ResponseEntity<>(createdActor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ActorDto>> getAllActors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("---------- getAllActors called with page: {}, size: {} ----------", page, size);
        Page<ActorDto> actors = actorService.getAllActorsWithPagination(page, size);
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> getActorById(@PathVariable Long id) {
        log.info("---------- getActorById called for id: {} ----------", id);
        ActorDto actor = actorService.getActorById(id);
        return new ResponseEntity<>(actor, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ActorDto> updateActor(
            @PathVariable Long id,
            @Valid @RequestBody ActorDto actorDto) {
        log.info("---------- updateActor called for id: {} ----------", id);
        ActorDto updatedActor = actorService.updateActor(id, actorDto);
        return new ResponseEntity<>(updatedActor, HttpStatus.OK);
    }

    // был затык @NotNull в документации
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean force) {
        log.info("---------- deleteActor called for id: {} with force: {} ----------", id, force);
        actorService.deleteActor(id, force);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<ActorDto>> getActorsByName(@RequestParam String name) {
        log.info("---------- getActorsByName called for name: {} ----------", name);
        List<ActorDto> actors = actorService.getActorsByName(name);
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }
}