package com.movieapi.mapper;

import com.movieapi.dto.ActorDto;
import com.movieapi.entity.Actor;
import org.springframework.stereotype.Component;

@Component
public class ActorMapper implements BaseMapper<Actor, ActorDto> {
    @Override
    public ActorDto toDTO(Actor actor) {
        if (actor == null) {
            throw new NullPointerException("Actor cannot be null");
        }
        return ActorDto.builder()
                .id(actor.getId())
                .name(actor.getName())
                .birthDate(actor.getBirthDate())
                .build();
    }

    @Override
    public Actor toEntity(ActorDto actorDto) {
        if (actorDto == null) {
            throw new NullPointerException("ActorDto cannot be null");
        }
        return Actor.builder()
                .id(actorDto.getId())
                .name(actorDto.getName())
                .birthDate(actorDto.getBirthDate())
                .build();
    }
}
