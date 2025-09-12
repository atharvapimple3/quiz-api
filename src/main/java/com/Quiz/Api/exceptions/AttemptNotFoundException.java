package com.Quiz.Api.exceptions;

public class AttemptNotFoundException extends RuntimeException{
    public AttemptNotFoundException(String message) {
        super(message);
    }
}
