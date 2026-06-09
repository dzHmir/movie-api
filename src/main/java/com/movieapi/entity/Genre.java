package com.movieapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Genre name cannot be blank")
    @Size(max = 100, message = "Genre name cannot exceed 100 characters")
    private String name;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Movie> movies = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre genre)) return false;
        return id != null && id.equals(genre.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}