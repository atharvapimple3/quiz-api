package com.Quiz.Api.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Map<String, Object> buildErrorDetails(String message, int statusValue, String uri) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", message);
        errorDetails.put("status", statusValue);
        errorDetails.put("uri", uri);
        return errorDetails;
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<Object> handleQuizNotFound(QuizNotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(buildErrorDetails(e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(buildErrorDetails(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException e, HttpServletRequest request) {
        return new ResponseEntity<>(buildErrorDetails(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(AttemptNotFoundException.class)
    public ResponseEntity<Object> handleAttemptNotFound(AttemptNotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(buildErrorDetails(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<Object> handleQuestionNotFound(QuestionNotFoundException e, HttpServletRequest request){
        return new ResponseEntity<>(buildErrorDetails(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI()),
                HttpStatus.NOT_FOUND
        );
    }


}
