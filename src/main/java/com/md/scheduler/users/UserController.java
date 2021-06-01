package com.md.scheduler.users;

import com.md.scheduler.configuration.api.errors.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class UserController implements UserApi {

    private final UserService service;

    @Override
    public UserInfo getUser(Long id) throws EntityNotFoundException {
        return service.getById(id);
    }
}
