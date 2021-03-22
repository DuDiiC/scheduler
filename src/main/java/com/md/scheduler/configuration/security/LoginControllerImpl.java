package com.md.scheduler.configuration.security;

import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
class LoginControllerImpl implements LoginController {

    @Override
    public void login(AuthCredentials credentials) {
    }
}
