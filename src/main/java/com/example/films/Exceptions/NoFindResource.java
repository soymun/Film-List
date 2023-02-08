package com.example.films.Exceptions;

public class NoFindResource extends RuntimeException{

    public NoFindResource(String message) {
        super(message);
    }
}
