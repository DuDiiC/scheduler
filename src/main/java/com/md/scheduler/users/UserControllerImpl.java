package com.md.scheduler.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
class UserControllerImpl implements UserController {

    private final UserService service;

    @Override
    public ResponseEntity<?> getUser(Long id) {
        try {
            return ResponseEntity.ok(
                    new UserInfo(service.getById(id))
            );
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
