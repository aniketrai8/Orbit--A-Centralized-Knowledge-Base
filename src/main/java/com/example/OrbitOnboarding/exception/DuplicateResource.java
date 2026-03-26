package com.example.OrbitOnboarding.exception;


//Attempting to use username,emails etc, that do not exist
public class DuplicateResource extends RuntimeException {
    public DuplicateResource(String message) {
        super(message);
    }
}
