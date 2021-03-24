package com.md.scheduler.users.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1")
interface RegisterController {

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegisterDto newUser) throws UserAlreadyExistAuthenticationException;
}
