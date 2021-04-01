package com.md.scheduler.configuration.exception;

import com.md.scheduler.users.registration.UserAlreadyExistAuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        ApiException validationException = ApiException.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message("Validation error")
                .debugMessage(ex.getLocalizedMessage())
                .build();
        validationException.addValidationException(ex.getBindingResult().getGlobalErrors());
        validationException.addValidationExceptions(ex.getBindingResult().getFieldErrors());

        return new ResponseEntity<>(
                validationException,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserAlreadyExistAuthenticationException.class)
    ResponseEntity<Object> handler(UserAlreadyExistAuthenticationException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiException.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.toString())
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                HttpStatus.CONFLICT
        );
    }
}
