package com.movieapi.service;

import com.movieapi.entity.Actor;
import com.movieapi.entity.Movie;
import com.movieapi.dto.ActorDto;
import com.movieapi.mapper.ActorMapper;
import com.movieapi.repository.ActorRepository;
import com.movieapi.repository.MovieRepository;
import com.movieapi.exception.ResourceNotFoundException;
import com.movieapi.exception.ResourceHasRelationsException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ActorMapper actorMapper;

    public ActorDto createActor(ActorDto actorDto) {
        Actor actor = actorMapper.toEntity(actorDto);
        Actor savedActor = actorRepository.save(actor);
        return actorMapper.toDTO(savedActor);
    }

    public List<ActorDto> getAllActors() {
        return getAllActorsWithPagination(0, 10).getContent();
    }

    public Page<ActorDto> getAllActorsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Actor> actorsPage = actorRepository.findAll(pageable);
        return actorsPage.map(actorMapper::toDTO);
    }

    public ActorDto getActorById(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", id));
        return actorMapper.toDTO(actor);
    }

    public ActorDto updateActor(Long id, ActorDto actorDto) {
        Actor existingActor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", id));

        if (actorDto.getName() != null) {
            existingActor.setName(actorDto.getName());
        }
        if (actorDto.getBirthDate() != null) {
            existingActor.setBirthDate(actorDto.getBirthDate());
        }

        Actor updatedActor = actorRepository.save(existingActor);
        return actorMapper.toDTO(updatedActor);
    }

    public void deleteActor(Long id) {
        deleteActor(id, false);
    }

    public void deleteActor(Long id, boolean force) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", id));
        List<Movie> associatedMovies = movieRepository.findByActors_Id(id);

        if (!associatedMovies.isEmpty()) {
            if (!force) {
                throw new ResourceHasRelationsException(
                        String.format("Unable to delete actor '%s' as they are associated with %d movies",
                                actor.getName(), associatedMovies.size()));
            } else {
                for (Movie movie : associatedMovies) {
                    if (movie.getActors() != null) {
                        movie.getActors().remove(actor);
                        movieRepository.save(movie);
                    }
                }
            }
        }

        actorRepository.deleteById(id);
    }

    public List<ActorDto> getActorsByName(String name) {
        List<Actor> allActors = actorRepository.findAll();
        List<Actor> filteredActors = allActors.stream()
                .filter(actor -> actor.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
        return filteredActors.stream()
                .map(actorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<Long> getMovieIdsForActor(Long actorId) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", actorId));

        return actor.getMovies().stream()
                .map(Movie::getId)
                .collect(Collectors.toList());
    }
}
