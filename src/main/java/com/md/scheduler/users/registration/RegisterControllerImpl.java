package com.md.scheduler.users.registration;

import com.md.scheduler.users.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.net.URI;

@RestController
@RequiredArgsConstructor
class RegisterControllerImpl implements RegisterController {

    private final RegisterService registerService;

    public ResponseEntity<Serializable> register(RegisterDto newUser) throws UserAlreadyExistAuthenticationException {
        UserInfo registeredUser = registerService.register(newUser);
        return ResponseEntity
                .created(URI.create("/users/" + registeredUser.getId()))
                .build();
    }
}
