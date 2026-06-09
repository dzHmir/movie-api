package com.movieapi.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceType, Long id) {
        super(String.format("%s with id %d not found", resourceType, id));
    }
}