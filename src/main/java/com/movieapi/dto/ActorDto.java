package com.movieapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActorDto {
    private Long id;

    @NotBlank(message = "Actor name cannot be blank")
    @Size(max = 100, message = "Actor name cannot exceed 100 characters")
    private String name;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
}