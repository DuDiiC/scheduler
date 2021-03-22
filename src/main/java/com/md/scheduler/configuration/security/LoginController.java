package com.md.scheduler.configuration.security;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

interface LoginController {

    @PostMapping("/login")
    void login(@RequestBody AuthCredentials credentials);
}
