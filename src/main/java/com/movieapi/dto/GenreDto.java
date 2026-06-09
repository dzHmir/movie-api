package com.movieapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    private Long id;

    @NotBlank(message = "Genre name cannot be blank")
    @Size(max = 100, message = "Genre name cannot exceed 100 characters")
    private String name;
}