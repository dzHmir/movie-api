package com.movieapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Movie title cannot be blank")
    @Size(max = 255, message = "Movie title cannot exceed 255 characters")
    private String title;

    @Column(name = "release_year")
    @Min(value = 1888, message = "Release year cannot be before 1888 (first movie)")
    @Max(value = 2100, message = "Release year cannot be in the future beyond 2100")
    private Integer releaseYear;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 500, message = "Duration cannot exceed 500 minutes")
    private Integer duration;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
        return id != null && id.equals(movie.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}