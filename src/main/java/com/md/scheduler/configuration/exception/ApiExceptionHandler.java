package com.md.scheduler.configuration.exception;

import com.md.scheduler.users.registration.UserAlreadyExistAuthenticationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistAuthenticationException.class)
    ResponseEntity<Object> handler(UserAlreadyExistAuthenticationException ex, WebRequest request) {
        return new ResponseEntity<>(
                        ApiException.builder()
                                .type("/errors/user-already-exist")
                                .title(ex.getMessage())
                                .status(HttpStatus.CONFLICT.value())
                                .detail("Select other username, because your choice is already in use in our system")
                                .instance(request.getDescription(false))
                        .build(),
                new HttpHeaders(),
                HttpStatus.CONFLICT
        );
    }
}
