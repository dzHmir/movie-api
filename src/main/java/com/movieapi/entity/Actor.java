package com.movieapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "actors")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Actor name cannot be blank")
    @Size(max = 100, message = "Actor name cannot exceed 100 characters")
    private String name;

    @Column(name = "birth_date")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    private Set<Movie> movies = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor actor)) return false;
        return id != null && id.equals(actor.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}