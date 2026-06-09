package com.movieapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private Long id;

    @NotBlank(message = "Movie title cannot be blank")
    @Size(max = 255, message = "Movie title cannot exceed 255 characters")
    private String title;

    @Min(value = 1888, message = "Release year cannot be before 1888 (first movie)")
    @Max(value = 2100, message = "Release year cannot be in the future beyond 2100")
    private Integer releaseYear;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 500, message = "Duration cannot exceed 500 minutes")
    private Integer duration;

    private Set<Long> genreIds;
    private Set<Long> actorIds;
}