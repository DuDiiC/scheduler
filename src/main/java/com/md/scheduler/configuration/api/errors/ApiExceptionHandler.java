package com.md.scheduler.configuration.api.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        var validationException = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message("Validation error")
                .build();
        validationException.addValidationErrorDetail(ex.getBindingResult().getGlobalErrors());
        validationException.addValidationErrorDetails(ex.getBindingResult().getFieldErrors());

        return new ResponseEntity<>(
                validationException,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {

        var entityNotFoundException = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.toString())
                .message(ex.getMessage())
                .build();
        entityNotFoundException.addEntityNotFoundExceptions(ex.getRejectedFields());

        return new ResponseEntity<>(
                entityNotFoundException,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(EnumValueNotFoundException.class)
    ResponseEntity<Object> handler(EnumValueNotFoundException ex) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<Object> handler(UsernameNotFoundException ex) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    ResponseEntity<Object> handler(ResourceAlreadyExistsException ex) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.toString())
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<Object> handler(AuthenticationException ex) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNAUTHORIZED.toString())
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                HttpStatus.UNAUTHORIZED
        );
    }
}
