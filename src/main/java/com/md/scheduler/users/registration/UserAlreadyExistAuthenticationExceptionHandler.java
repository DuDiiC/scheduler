package com.md.scheduler.users.registration;

import com.md.scheduler.configuration.exception.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class UserAlreadyExistAuthenticationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistAuthenticationException.class)
    ResponseEntity<Object> handler(WebRequest request) {
        return new ResponseEntity<>(
                        ApiException.builder()
                                .type("/errors/user-already-exist")
                                .title("User with selected username already exists")
                                .status(HttpStatus.CONFLICT.value())
                                .detail("Select other username, because your choice is already in use in our system")
                                .instance(request.getDescription(false))
                        .build(),
                new HttpHeaders(),
                HttpStatus.CONFLICT
        );
    }
}
