package com.md.scheduler.users.registration;

import com.md.scheduler.configuration.api.errors.ResourceAlreadyExistsException;
import com.md.scheduler.users.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class RegisterController implements RegisterApi {

    private final RegisterService registerService;

    public UserInfo register(NewUser newUser) throws ResourceAlreadyExistsException {
        return registerService.register(newUser);
    }
}
