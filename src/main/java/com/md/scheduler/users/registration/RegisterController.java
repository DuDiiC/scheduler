package com.md.scheduler.users.registration;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.Serializable;

@RequestMapping("/api/v1")
@Tag(name = "registration", description = "registration API to create new user")
interface RegisterController {

    @PostMapping("/register")
    ResponseEntity<Serializable> register(@RequestBody @Valid NewUser newUser) throws UserAlreadyExistAuthenticationException;
}
