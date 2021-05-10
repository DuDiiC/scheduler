package com.md.scheduler.users.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.Serializable;

@RequestMapping("/api/v1")
interface RegisterController {

    @PostMapping("/register")
    ResponseEntity<Serializable> register(@RequestBody @Valid NewUser newUser) throws UserAlreadyExistAuthenticationException;
}
