package com.movieapi.exception;

public class ResourceHasRelationsException extends RuntimeException {
    public ResourceHasRelationsException(String message) {
        super(message);
    }
}