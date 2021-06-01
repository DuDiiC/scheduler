package com.md.scheduler.configuration.security;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@Tag(name = "login")
class AuthenticationApi {

    @PostMapping
    public BearerToken login(@RequestBody AuthCredentials authCredentials){
        return new BearerToken();
    }
}
